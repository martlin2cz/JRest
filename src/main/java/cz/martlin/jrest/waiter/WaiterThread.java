package cz.martlin.jrest.waiter;

import cz.martlin.jrest.misc.Interruptable;

/**
 * Thread to run waiter in.
 * 
 * @author martin
 *
 */
public class WaiterThread extends Thread implements Interruptable {

	public WaiterThread(JRestWaiter waiter) {
		super(new WaiterRunnable(waiter), "WaiterThread");
	}

}
