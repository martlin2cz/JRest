package cz.martlin.jrest.impl.jarmil.serializers;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponseStatus;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.protocol.serializers.BaseShellLikeSerializer;

/**
 * The {@link BaseShellLikeSerializer} for the {@link JarmilRequest} and
 * {@link JarmilResponse}.
 * 
 * Uses the following format (for request and response):
 * 
 * <pre>
 * [target type] [target identifier] [method] [arg 1] ... [arg n]
 * [status] [value] [type]
 * </pre>
 * 
 * @author martin
 *
 */
public class JarmilShellLikeSerializer extends BaseShellLikeSerializer<JarmilRequest, JarmilResponse> {

	private static final TargetsSerializer targets = new TargetsSerializer();
	private static final ValuesSerializer values = new ValuesSerializer();

	public JarmilShellLikeSerializer() {
	}

	@Override
	protected JarmilResponse listToResponse(List<String> list) throws Exception {

		JarmilResponseStatus status = JarmilResponseStatus.valueOf(list.get(0));
		Class<?> type = Class.forName(list.get(2));
		Object data = values.deserialize(list.get(1), type);

		return new JarmilResponse(status, data, type);
	}

	@Override
	protected List<String> responseToList(JarmilResponse response) throws Exception {
		List<String> list = new ArrayList<>(3);

		String status = response.getStatus().name();
		list.add(status);

		String data = values.serialize(response.getData());
		list.add(data);

		String type = response.getType().getName();
		list.add(type);

		return list;
	}

	@Override
	protected JarmilRequest listToRequest(List<String> list) throws Exception {

		if (list.size() < 3) {
			throw new IllegalArgumentException("Request need to have at least three parts, but have " + list.size());
		}

		TargetOnGuest target = deserializeTarget(list);
		String method = deserializeMethod(list);
		List<Object> parameters = deserializeParameters(list);

		return new JarmilRequest(target, method, parameters);
	}

	@Override
	protected List<String> requestToList(JarmilRequest request) throws Exception {

		List<String> list = new ArrayList<>(2 + request.getParameters().size());

		serializeTarget(request.getTarget(), list);
		serializeMethod(request.getMethod(), list);
		serializeParameters(request.getParameters(), list);

		return list;
	}

	private TargetOnGuest deserializeTarget(List<String> list) throws IllegalArgumentException {

		TargetOnGuest target = targets.deserialize(list);
		return target;
	}

	private String deserializeMethod(List<String> list) {

		String name = list.get(2);
		return name;
	}

	private List<Object> deserializeParameters(List<String> list) throws Exception {
		List<Object> result = new ArrayList<>(list.size() - 3);

		for (String parameter : list.subList(3, list.size())) {
			Object param = values.deserialize(parameter);
			result.add(param);
		}

		return result;
	}

	private void serializeTarget(TargetOnGuest target, List<String> list) {
		targets.serialize(target, list);
	}

	private void serializeMethod(String name, List<String> list) {
		list.add(name);
	}

	private void serializeParameters(List<Object> parameters, List<String> list) throws Exception {
		for (Object parameter : parameters) {
			String param = values.serialize(parameter);
			list.add(param);
		}
	}

}
