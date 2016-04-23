package cz.martlin.jrest.impl.jarmil.handler;

import java.lang.reflect.Method;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.misc.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.misc.MethodsFinder;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.waiter.NewObjectOnWaiterTarget;
import cz.martlin.jrest.impl.jarmil.targets.waiter.ObjectOnWaiterTarget;
import cz.martlin.jrest.impl.jarmil.targets.waiter.StaticClassOnWaiterTarget;

public class JarmilRequestsPerformer {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	private final MethodsFinder finder = new MethodsFinder();

	private final JarmilEnvironment environment;

	public JarmilRequestsPerformer(JarmilEnvironment environment) {
		super();
		this.environment = environment;
	}

	public JarmilResponse perform(JarmilRequest request) {
		LOG.info("Performing request: " + request);

		Class<? extends JarmilTarget> clazz;
		TargetOnWaiter waiterTarget;
		Method method;
		try {
			waiterTarget = findWaiterTarget(request.getTarget());
			clazz = findTargetClass(waiterTarget);
			method = findMethod(clazz, waiterTarget, request.getMethod(), request.getParameters());
		} catch (Exception e) {
			return processUnknownTarget(request, e);
		}

		Object response;
		try {
			response = invokeMethod(waiterTarget, method, request.getParameters());
		} catch (Exception e) {
			return processInvocationError(request, e);
		}

		return processResponse(response, method);

	}

	private TargetOnWaiter findWaiterTarget(TargetOnGuest target) {
		TargetOnWaiter waiterTarget = environment.find(target);

		if (waiterTarget == null) {
			throw new IllegalArgumentException("Target " + target + " not found in current environment");
		}

		return waiterTarget;
	}

	private Class<? extends JarmilTarget> findTargetClass(TargetOnWaiter waiterTarget) throws IllegalArgumentException {

		switch (waiterTarget.getType()) {
		case NEW:
			NewObjectOnWaiterTarget noowt = (NewObjectOnWaiterTarget) waiterTarget;
			return noowt.getClazz();
		case OBJECT:
			ObjectOnWaiterTarget oowt = (ObjectOnWaiterTarget) waiterTarget;
			return oowt.getTargetObject().getClass();
		case STATIC:
			StaticClassOnWaiterTarget scowt = (StaticClassOnWaiterTarget) waiterTarget;
			return scowt.getClazz();
		default:
			throw new IllegalArgumentException("Unknown target type" + waiterTarget.getType());
		}
	}

	private Method findMethod(Class<? extends JarmilTarget> clazz, TargetOnWaiter waiterTarget, String method,
			List<Object> parameters) throws NoSuchMethodException {
		boolean neededNonStatic = waiterTarget.getType() != TargetType.STATIC;
		return finder.findMethod(clazz, method, parameters, neededNonStatic);
	}

	private Object invokeMethod(TargetOnWaiter waiterTarget, Method method, List<Object> parameters) throws Exception {
		return waiterTarget.invoke(method, parameters);
	}

	private JarmilResponse processResponse(Object response, Method method) {
		if (response != null) {
			return JarmilResponse.createNonnullOk(response);
		} else {
			Class<?> type = method.getReturnType();
			return JarmilResponse.createNullOk(type);
		}
	}

	private JarmilResponse processUnknownTarget(JarmilRequest request, Exception e) {
		LOG.error("Target (or method) of request " + request + " not found", e);
		return JarmilResponse.createUnknownTarget(request.getTarget(), request.getMethod(), e);
	}

	private JarmilResponse processInvocationError(JarmilRequest request, Exception e) {
		LOG.error("Method invocation of request " + request + " failed", e);
		return JarmilResponse.createIvocationError(e);
	}

}
