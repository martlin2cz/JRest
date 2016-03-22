package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.Interruptable;

public class WaiterRunnable implements Runnable, Interruptable {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JRestWaiter waiter;
	private boolean interrupted;

	public WaiterRunnable(JRestWaiter waiter) {
		this.waiter = waiter;
	}

	public void run() {
		waiter.runWaiting();
	}

	@Deprecated
	public void interrupt() {
		this.interrupted = true;
	}

}
