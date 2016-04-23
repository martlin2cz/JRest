package cz.martlin.jrest.impl.jarmil.handlers;

import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.waiter.JRestWaiter;

public class WaiterStopper {

	public static final String METHOD_NAME = "stop";

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

}
