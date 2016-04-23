package cz.martlin.jrest.impl.jarmil.handler;

import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.waiter.JRestWaiter;

/**
 * The class performing stopping of waiter to be used by {@link JarmilHandler}.
 * 
 * @author martin
 *
 */
public class WaiterStopper implements JarmilTarget {

	public static final String OBJECT_NAME = "waiterStopper";
	public static final String METHOD_NAME = "stop";
	private static final String DESCRIPTION = "Performs stopping of waiter, with a method " + METHOD_NAME;

	private JRestWaiter<JarmilRequest, JarmilResponse> waiter;

	public WaiterStopper() {
	}

	public void setWaiter(JRestWaiter<JarmilRequest, JarmilResponse> waiter) {
		this.waiter = waiter;
	}

	public void stop() {
		if (waiter != null) {
			waiter.stopWaiter(null);
		}
	}

	public void stop(String message) {
		if (waiter != null) {
			waiter.stopWaiter(message);
		}
	}

	@Override
	public String getJarmilTargetDescription() {
		return DESCRIPTION;
	}

}
