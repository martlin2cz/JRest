package cz.martlin.jrest.impl.jarmil.handlers;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cz.martlin.jrest.misc.JRestException;

public class MethodInvoker {

	public MethodInvoker() {
	}

	public Object invoke(Object clazz, Object object, Method method, List<Object> parameters) throws JRestException {
		try {
			if (parameters.isEmpty()) {
				return method.invoke(object);
			} else {
				Object[] array = new ArrayList<>(parameters).toArray();
				return method.invoke(object, array);
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			throw new JRestException("Method invocation failed", e);
		}
	}

	public Class<?> typeof(Object clazz, Method method, List<Object> parameters) {
		return method.getReturnType();
	}

}
