package cz.martlin.jrest.impl.jarmil.handlers;

import cz.martlin.jrest.impl.jarmil.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.JarmilResponse;
import cz.martlin.jrest.waiter.JRestWaiter;

public class WaiterStopper {

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
