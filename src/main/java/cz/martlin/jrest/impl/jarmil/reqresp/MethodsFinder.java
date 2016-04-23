package cz.martlin.jrest.impl.jarmil.reqresp;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO doc
public class MethodsFinder {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public MethodsFinder() {
	}

	public Method findMethod(Class<?> clazz, String name, List<Object> callableWith, boolean neededNonStatic)
			throws NoSuchMethodException, IllegalStateException {
		LOG.info("Searching (static? {}) method on class {} of name {} to be called with {}.", !neededNonStatic,
				clazz.getName(), name, callableWith);

		Set<Method> methods;

		methods = allMethods(clazz);
		methods = filterByName(methods, name);
		methods = filterByStaticness(methods, neededNonStatic);
		methods = filterByArgsCount(methods, callableWith.size());
		methods = filterByArgs(methods, callableWith);

		if (methods.isEmpty()) {
			throw new NoSuchMethodException("Cannot find the method " + name + " on " + clazz.getName());
		} else if (methods.size() > 1) {
			throw new IllegalStateException("Method " + name + " is ambigous");
		} else {
			return methods.iterator().next();
		}
	}

	private Set<Method> filterByArgs(Set<Method> methods, List<Object> callableWith) {
		LOG.trace("Removing methods such that cannot be invoked with {}. Removing from: {}", callableWith, methods);

		Set<Method> result = new HashSet<>(methods.size());

		List<Class<?>> calee = types(callableWith);

		for (Method method : methods) {
			List<Class<?>> caled = types(method);
			if (matches(calee, caled)) {
				result.add(method);
			}
		}

		LOG.debug("By args removed {} methods, remains {}", methods.size() - result.size(), result.size());
		return result;
	}

	private Set<Method> filterByName(Set<Method> methods, String name) {
		LOG.trace("Removing methods not named {}. Removing from: ", name, methods);

		Set<Method> result = new HashSet<>(methods.size());

		for (Method method : methods) {
			if (method.getName().equals(name)) {
				result.add(method);
			}
		}

		LOG.debug("By name removed {} methods, remains {}", methods.size() - result.size(), result.size());
		return result;
	}

	private Set<Method> filterByArgsCount(Set<Method> methods, int count) {
		LOG.trace("Removing methods with no {} arguments. Removing from: {}", count, methods);

		Set<Method> result = new HashSet<>(methods.size());

		for (Method method : methods) {
			if (method.getParameterCount() == count) {
				result.add(method);
			}
		}

		LOG.debug("By args count removed {} methods, remains {}", methods.size() - result.size(), result.size());
		return result;
	}

	private Set<Method> filterByStaticness(Set<Method> methods, boolean neededNonstatic) {
		LOG.trace("Removing nonstatic? ({}) methods. Removing from: {}", neededNonstatic, methods);

		Set<Method> result = new HashSet<>(methods.size());

		for (Method method : methods) {
			boolean isStatic = Modifier.isStatic(method.getModifiers());
			if (neededNonstatic == !isStatic) {
				result.add(method);
			}
		}

		LOG.debug("By staticness removed {} methods, remains {}", methods.size() - result.size(), result.size());
		return result;
	}

	private Set<Method> allMethods(Class<?> clazz) {
		LOG.trace("Retrieving all class methods. Of class: {}", clazz.getName());

		Method[] array = clazz.getMethods();
		List<Method> list = Arrays.asList(array);
		Set<Method> set = new HashSet<>(list);

		LOG.debug("Got {} methods", set.size());
		return set;
	}

	private List<Class<?>> types(List<Object> objects) {
		List<Class<?>> classes = new ArrayList<>(objects.size());

		for (Object object : objects) {
			if (object == null) {
				classes.add(null);
			} else {
				classes.add(object.getClass());
			}
		}

		return classes;
	}

	private List<Class<?>> types(Method method) {
		List<Class<?>> types = Arrays.asList(method.getParameterTypes());
		return types;
	}

	private boolean matches(List<Class<?>> calee, List<Class<?>> called) {
		Iterator<Class<?>> caleeIter = calee.iterator();
		Iterator<Class<?>> calledIter = called.iterator();

		while (caleeIter.hasNext() && calledIter.hasNext()) {
			Class<?> caleeType = caleeIter.next();
			Class<?> calledType = calledIter.next();

			if (caleeType == null) {
				continue;
			}

			if (caleeType.equals(calledType)) {
				continue;
			}

			if (!calledType.isAssignableFrom(caleeType)) {
				return false;
			}
		}

		return caleeIter.hasNext() == calledIter.hasNext();
	}
}
