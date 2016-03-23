package cz.martlin.jrest.waiter;

/**
 * Runnable of waiter thread. Runs given waiter's waiting.
 * 
 * @author martin
 *
 */
public class WaiterRunnable implements Runnable {

	private final JRestWaiter waiter;

	public WaiterRunnable(JRestWaiter waiter) {
		this.waiter = waiter;
	}

	public void run() {
		waiter.runWaiter();
	}

}
