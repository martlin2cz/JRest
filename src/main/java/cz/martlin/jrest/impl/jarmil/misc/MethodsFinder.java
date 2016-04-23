package cz.martlin.jrest.impl.jarmil.misc;

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

/**
 * An utility which tries to find method by given criteria. The criteria are the
 * declaring class, name of the method, the list of objects (which must be
 * method callable with) and static/not static specifier. This class tries to
 * find exactly one method, else fails.
 * 
 * @author martin
 *
 */
public class MethodsFinder {
	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public MethodsFinder() {
	}

	/**
	 * Tries to find method by given arguments. If is not exactly 1 matching
	 * method found, the exception is thrown.
	 * 
	 * @param clazz
	 * @param name
	 * @param callableWith
	 * @param neededNonStatic
	 * @return
	 * @throws NoSuchMethodException
	 */
	public Method findMethod(Class<?> clazz, String name, List<Object> callableWith, boolean neededNonStatic)
			throws NoSuchMethodException {

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
			throw new NoSuchMethodException("Method " + name + " is ambigous, found " + methods.size() + " matching");
		} else {
			return methods.iterator().next();
		}
	}

	/**
	 * Filters the given list of methods by the match of arguments types against
	 * the given objects to be called with.
	 * 
	 * @param methods
	 * @param callableWith
	 * @return
	 */
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

	/**
	 * Filters the given list of methods by the method name.
	 * 
	 * @param methods
	 * @param name
	 * @return
	 */
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

	/**
	 * Filters the given list of methods by match to given number of arguments
	 * 
	 * @param methods
	 * @param count
	 * @return
	 */
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

	/**
	 * Filters the given list of methods by static/not static flag.
	 * 
	 * @param methods
	 * @param neededNonstatic
	 * @return
	 */
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

	/**
	 * Lists all methods of given class.
	 * 
	 * @param clazz
	 * @return
	 */
	private Set<Method> allMethods(Class<?> clazz) {
		LOG.trace("Retrieving all class methods. Of class: {}", clazz.getName());

		Method[] array = clazz.getMethods();
		List<Method> list = Arrays.asList(array);
		Set<Method> set = new HashSet<>(list);

		LOG.debug("Got {} methods", set.size());
		return set;
	}

	/**
	 * Clear, isn't it? Object -> its type, null -> null. What else?
	 * 
	 * @param objects
	 * @return
	 */
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

	/**
	 * For given method returns list of its parameter types.
	 * 
	 * @param method
	 * @return
	 */
	private List<Class<?>> types(Method method) {
		List<Class<?>> types = Arrays.asList(method.getParameterTypes());
		return types;
	}

	/**
	 * Returns true if given calee types are null or subtypes of corresponding
	 * called. Or something like that.
	 * 
	 * @param calee
	 * @param called
	 * @return
	 */
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
