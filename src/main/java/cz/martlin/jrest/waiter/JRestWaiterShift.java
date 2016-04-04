package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.protocol.WaiterProtocol;

/**
 * Represents shift of {@link JRestWaiter}. The shift can be started and stoped
 * whenever. In fact just simply wraps {@link JRestWaiter} in <strong>separate
 * thread</strong>.
 * 
 * @author martin
 *
 */
public class JRestWaiterShift {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JRestWaiter waiter;
	private final WaiterThread thread;
	private final WaiterRunnable body;

	/**
	 * Creates instance with given protocol and commands processor.
	 * 
	 * @param protocol
	 * @param processor
	 */
	public JRestWaiterShift(WaiterProtocol protocol) {
		waiter = new JRestWaiter(protocol);
		body = new WaiterRunnable(waiter);
		thread = new WaiterThread(body);
	}

	/**
	 * Starts waiter.
	 */
	public void startWaiter() {
		log.debug("Waiter's shift starting..");

		thread.start();

		log.info("Waiter's shift started");
	}

	/**
	 * Stops waiter.
	 */
	public void stopWaiter() {
		log.debug("Waiter's shift stopping..");

		waiter.stopWaiter("Shift stop invoked");
		thread.interrupt();

		try {
			thread.join();
		} catch (InterruptedException e) {
		}

		log.info("Waiter's shift stopped");
	}
}
