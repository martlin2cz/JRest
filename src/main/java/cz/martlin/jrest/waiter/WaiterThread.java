package cz.martlin.jrest.waiter;

import cz.martlin.jrest.misc.Interruptable;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;

/**
 * Thread to run waiter in.
 * 
 * @author martin
 *
 */
public class WaiterThread<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> extends Thread implements Interruptable {

	public WaiterThread(JRestWaiter<RQT, RST> waiter) {
		super(new WaiterRunnable<RQT, RST>(waiter), "WaiterThread");
	}

}
