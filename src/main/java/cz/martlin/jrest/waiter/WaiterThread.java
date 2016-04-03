package cz.martlin.jrest.waiter;

import cz.martlin.jrest.misc.Interruptable;

/**
 * Thread to run waiter in.
 * 
 * @author martin
 *
 */
public class WaiterThread extends Thread implements Interruptable {

	public WaiterThread(WaiterRunnable body) {
		super(body, "WaiterT");
//		setDaemon(true);
	}

}
