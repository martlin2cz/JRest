package cz.martlin.jrest.protocol.serializers.jarmil;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.MethodsFinder;
import cz.martlin.jrest.protocol.reqresps.jarmil.JarmilRequest;
import cz.martlin.jrest.protocol.reqresps.jarmil.JarmilResponse;
import cz.martlin.jrest.protocol.reqresps.jarmil.JarmilResponseStatus;
import cz.martlin.jrest.protocol.serializers.BaseShellLikeSerializer;

public class JarmilShellLikeSerializer extends BaseShellLikeSerializer<JarmilRequest, JarmilResponse> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private static final ValuesSerializer serializer = new ValuesSerializer();
	private static final MethodsFinder finder = new MethodsFinder();
	private static final String NO_VALUE = " ";

	private final Map<String, Object> environment;

	public JarmilShellLikeSerializer(Map<String, Object> environment) {
		this.environment = environment;
	}

	@Override
	protected JarmilResponse listToResponse(List<String> list) throws ClassNotFoundException, IllegalArgumentException {

		JarmilResponseStatus status = JarmilResponseStatus.valueOf(list.get(0));
		Class<?> type = Class.forName(list.get(2));
		Object data = serializer.deserialize(list.get(1), type);

		return new JarmilResponse(status, data, type);
	}

	@Override
	protected List<String> responseToList(JarmilResponse response) {
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
	protected JarmilRequest listToRequest(List<String> list)
			throws IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, IllegalStateException {

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
			Object object = environment.get(objectName);
			if (object == null) {
				throw new IllegalStateException("Object of name " + objectName + " not found");
			}
			LOG.trace("Found object of name " + objectName);
			return objectName;
		}
	}

	private Class<?> deserializeClass(String objectName, List<String> list) throws ClassNotFoundException {
		String classSpec = list.get(0);

		if (NO_VALUE.equals(classSpec)) {
			LOG.trace("No class specified, will use the object's ");
			Object object = environment.get(objectName);
			if (object == null) {
				throw new IllegalArgumentException("No such object: " + objectName);
			}
			return object.getClass();
		} else {
			Class<?> clazz = Class.forName(classSpec);
			LOG.trace("Specified class " + clazz.getName() + " found");
			return clazz;
		}
	}

	private List<Object> deserializeParameters(List<String> list) {
		List<Object> result = new ArrayList<>(list.size() - 3);

		for (String parameter : list.subList(3, list.size())) {
			Object param = serializer.deserialize(parameter);
			result.add(param);
		}

		return result;
	}

	@Override
	protected List<String> requestToList(JarmilRequest request) {
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

		Object object = environment.get(name);
		if (object == null) {
			return request.getClazz().getName();
		}

		return NO_VALUE;
	}

	private void serializeParameters(JarmilRequest request, List<String> list) {
		for (Object parameter : request.getParameters()) {
			String param = serializer.serialize(parameter);
			list.add(param);
		}
	}

}
