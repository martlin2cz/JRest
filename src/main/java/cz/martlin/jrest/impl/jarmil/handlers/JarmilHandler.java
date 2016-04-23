package cz.martlin.jrest.impl.jarmil.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.protocol.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

public class JarmilHandler implements RequestHandler<JarmilRequest, JarmilResponse> {

	private final Logger LOG = LoggerFactory.getLogger(getClass());

	public static final String WAITER_STOPPER_NAME = "waiterStopper";
	public static final String ECHOER_NAME = "echoer";

	private static final MethodInvoker invoker = new MethodInvoker();

	private final JarmilEnvironment environment;

	public JarmilHandler(JarmilEnvironment environment) {
		super();
		this.environment = update(environment);

	}

	@Override
	public void initialize(JRestWaiter<JarmilRequest, JarmilResponse> waiter) throws Exception {
		WaiterStopper stopper = (WaiterStopper) environment.findObject(WAITER_STOPPER_NAME);
		stopper.setWaiter(waiter);
	}

	@Override
	public void finish(JRestWaiter<JarmilRequest, JarmilResponse> waiter) throws Exception {
	}

	@Override
	public JarmilResponse handle(JarmilRequest request) throws Exception {
		if (request.getObject() != null) {
			String name = request.getObject();
			Object object = environment.findObject(name);

			if (object == null) {
				return JarmilResponse.createUnknownObjectTarget(name);
			}

			return invokeOnObject(object, request);
		} else if (request.getClazz() != null) {
			Class<?> clazz = request.getClazz();

			if (!environment.findClass(clazz)) {
				return JarmilResponse.createUnknownStaticTarget(clazz);
			}

			return invokeStatic(clazz, request);
		} else {
			return JarmilResponse.createUnknownTarget("Unspecified target");
		}
	}

	private JarmilResponse invokeStatic(Class<?> clazz, JarmilRequest request) {
		return invoke(clazz, null, request);
	}

	private JarmilResponse invokeOnObject(Object object, JarmilRequest request) {
		return invoke(object.getClass(), object, request);
	}

	private JarmilResponse invoke(Object clazz, Object object, JarmilRequest request) {
		Object value;
		try {
			value = invoker.invoke(clazz, object, request.getMethod(), request.getParameters());
		} catch (JRestException e) {
			LOG.error("Invocation of method of request " + request + " failed", e);
			return JarmilResponse.createIvocationError(e);
		}

		if (value != null) {
			return JarmilResponse.createNonullOk(value);
		} else {
			Class<?> type = invoker.typeof(clazz, request.getMethod(), request.getParameters());
			return JarmilResponse.createNullOk(type);
		}
	}

	private static JarmilEnvironment update(JarmilEnvironment environment) {
		environment.addObject(WAITER_STOPPER_NAME, new WaiterStopper());
		environment.addObject(ECHOER_NAME, new Echoer());
		return environment;
	}

}
