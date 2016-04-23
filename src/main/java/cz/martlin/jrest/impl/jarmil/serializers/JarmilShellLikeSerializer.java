package cz.martlin.jrest.impl.jarmil.serializers;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.handlers.MethodsFinder;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponseStatus;
import cz.martlin.jrest.protocol.serializers.BaseShellLikeSerializer;

/**
 * The {@link BaseShellLikeSerializer} for the {@link JarmilRequest} and
 * {@link JarmilResponse}.
 * 
 * Uses the following format (for request and response):
 * 
 * <pre>
 * [class] [object] [method] [arg 1] ... [arg n]
 * [status] [value] [type]
 * </pre>
 * 
 * @author martin
 *
 */
public class JarmilShellLikeSerializer extends BaseShellLikeSerializer<JarmilRequest, JarmilResponse> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final ValuesSerializer serializer = new ValuesSerializer();
	private static final MethodsFinder finder = new MethodsFinder();
	private static final String NO_VALUE = " ";

	private final JarmilEnvironment environment;

	public JarmilShellLikeSerializer(JarmilEnvironment environment) {
		this.environment = environment;
	}

	@Override
	protected JarmilResponse listToResponse(List<String> list) throws Exception {

		JarmilResponseStatus status = JarmilResponseStatus.valueOf(list.get(0));
		Class<?> type = Class.forName(list.get(2));
		Object data = serializer.deserialize(list.get(1), type);

		return new JarmilResponse(status, data, type);
	}

	@Override
	protected List<String> responseToList(JarmilResponse response) throws Exception {
		List<String> list = new ArrayList<>(3);

		String status = response.getStatus().name();
		list.add(status);

		String data = serializer.serialize(response.getData());
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

		if (NO_VALUE.equals(list.get(0)) && NO_VALUE.equals(list.get(1))) {
			throw new IllegalArgumentException("No specified target nor a target class ");
		} else {
			LOG.debug("Trying to deserialize target from: " + list.get(0) + " and " + list.get(1));
		}

		String objectName = deserializeObject(list);

		Class<?> clazz = deserializeClass(objectName, list);

		List<Object> parameters = deserializeParameters(list);

		Method method = deserializeMethod(list, objectName, clazz, parameters);

		return new JarmilRequest(clazz, objectName, method, parameters);
	}

	private Method deserializeMethod(List<String> list, String objectName, Class<?> clazz, List<Object> parameters)
			throws NoSuchMethodException {

		String name = list.get(2);
		boolean nonStatic = objectName != null;

		return finder.findMethod(clazz, name, parameters, nonStatic);
	}

	private String deserializeObject(List<String> list) {
		String objectName = list.get(1);

		if (NO_VALUE.equals(objectName)) {
			LOG.trace("Object not specified");
			return null;
		} else {
			Object object = environment.findObject(objectName);
			if (object == null) {
				throw new IllegalStateException("Object of name " + objectName + " not found");
			}
			LOG.trace("Found object of name " + objectName);
			return objectName;
		}
	}

	private Class<?> deserializeClass(String objectName, List<String> list)
			throws IllegalArgumentException, ClassNotFoundException {

		String classSpec = list.get(0);

		if (NO_VALUE.equals(classSpec)) {
			LOG.trace("No class specified, will use the object's ");
			Object object = environment.findObject(objectName);
			if (object == null) {
				throw new IllegalArgumentException("No such object: " + objectName);
			}
			return object.getClass();
		} else {
			Class<?> clazz = Class.forName(classSpec);
			if (!environment.findClass(clazz)) {
				throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
			}
			LOG.trace("Specified class " + clazz.getName() + " found");
			return clazz;
		}
	}

	private List<Object> deserializeParameters(List<String> list) throws Exception {
		List<Object> result = new ArrayList<>(list.size() - 3);

		for (String parameter : list.subList(3, list.size())) {
			Object param = serializer.deserialize(parameter);
			result.add(param);
		}

		return result;
	}

	@Override
	protected List<String> requestToList(JarmilRequest request) throws Exception {
		if (request.getObject() == null && request.getClazz() == null) {
			throw new IllegalStateException("Class and/or object must be specified");
		}

		List<String> list = new ArrayList<>(3 + request.getParameters().size());

		String clazz = serializeClazz(request);
		list.add(clazz);

		if (request.getObject() != null) {
			list.add(request.getObject());
		} else {
			list.add(NO_VALUE);
		}

		list.add(request.getMethod().getName());

		serializeParameters(request, list);

		return list;
	}

	private String serializeClazz(JarmilRequest request) {

		String name = request.getObject();
		if (name == null) {
			return request.getClazz().getName();
		}

		Object object = environment.findObject(name);
		if (object == null) {
			Class<?> clazz = request.getClazz();
			if (!environment.findClass(clazz)) {
				throw new IllegalArgumentException("Unsupported class: " + clazz.getName());
			}
			return clazz.getName();
		}

		return NO_VALUE;
	}

	private void serializeParameters(JarmilRequest request, List<String> list) throws Exception {
		for (Object parameter : request.getParameters()) {
			String param = serializer.serialize(parameter);
			list.add(param);
		}
	}

}
