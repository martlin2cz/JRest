package cz.martlin.jrest.impl.jarmil.reqresp;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;

public class JarmilRequest implements JRestAbstractRequest {

	private static final MethodsFinder finder = new MethodsFinder();

	private final Class<?> clazz;
	private final String object;
	private final Method method;
	private final List<Object> parameters;

	public JarmilRequest(Class<?> clazz, String object, Method method, List<Object> parameters) {
		super();
		this.clazz = clazz;
		this.object = object;
		this.method = method;
		this.parameters = parameters;
	}

	public JarmilRequest(Class<?> clazz, Method method, List<Object> parameters) {
		super();
		this.clazz = clazz;
		this.object = null;
		this.method = method;
		this.parameters = parameters;
	}

	public JarmilRequest(String object, Method method, List<Object> parameters) {
		super();
		this.clazz = null;
		this.object = object;
		this.method = method;
		this.parameters = parameters;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getObject() {
		return object;
	}

	public Method getMethod() {
		return method;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((object == null) ? 0 : object.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JarmilRequest other = (JarmilRequest) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (object == null) {
			if (other.object != null)
				return false;
		} else if (!object.equals(other.object))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JarmilRequest [clazz=" + clazz + ", object=" + object + ", method=" + method + ", parameters="
				+ parameters + "]";
	}

	public static JarmilRequest create(Class<?> clazz, String objectName, String methodName, Object... parameters)
			throws JRestException {
		List<Object> params = Arrays.asList(parameters);
		Method meth;
		try {
			meth = finder.findMethod(clazz, methodName, params, true);
		} catch (NoSuchMethodException | IllegalStateException e) {
			throw new JRestException("Cannot create method on object " + objectName, e);
		}
		return new JarmilRequest(clazz, objectName, meth, params);
	}

	public static JarmilRequest create(Class<?> clazz, String methodName, Object... parameters) throws JRestException {
		List<Object> params = Arrays.asList(parameters);
		Method meth;
		try {
			meth = finder.findMethod(clazz, methodName, params, false);
		} catch (NoSuchMethodException | IllegalStateException e) {
			throw new JRestException("Cannot create static method", e);
		}
		return new JarmilRequest(clazz, null, meth, params);
	}

}
