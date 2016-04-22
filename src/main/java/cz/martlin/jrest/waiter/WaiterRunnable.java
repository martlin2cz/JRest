package cz.martlin.jrest.waiter;

import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;

/**
 * Runnable of waiter thread. Runs given waiter's waiting.
 * 
 * @author martin
 *
 */
public class WaiterRunnable<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> implements Runnable {

	private final JRestWaiter<RQT, RST> waiter;

	public WaiterRunnable(JRestWaiter<RQT,RST> waiter) {
		this.waiter = waiter;
	}

	public void run() {
		waiter.runWaiter();
	}

}
