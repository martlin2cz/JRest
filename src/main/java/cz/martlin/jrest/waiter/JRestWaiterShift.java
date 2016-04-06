package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.protocol.WaiterProtocol;

/**
 * Represents shift of {@link JRestWaiter}. The shift can be started and stoped
 * whenever. In fact just simply wraps {@link JRestWaiter} in <strong>separate
 * thread</strong>.
 * 
 * <strong>Warning: once is stopped, to start again is required to create new
 * instance</strong>
 * 
 * @author martin
 *
 */
public class JRestWaiterShift {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JRestWaiter waiter;
	private final WaiterThread thread;

	/**
	 * Creates instance with given protocol.
	 * 
	 * @param protocol
	 * @param processor
	 */
	public JRestWaiterShift(WaiterProtocol protocol) {
		this.waiter = new JRestWaiter(protocol);
		this.thread = new WaiterThread(waiter);
	}

	/**
	 * Creates instance with given yet existing waiter.
	 * 
	 * @param protocol
	 * @param processor
	 */
	public JRestWaiterShift(JRestWaiter waiter) {
		this.waiter = waiter;
		this.thread = new WaiterThread(waiter);
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

		waiter.awakeAndStopWaiter("Shift stop invoked");
		thread.interrupt();

		try {
			thread.join();
		} catch (InterruptedException e) {
		}

		log.info("Waiter's shift stopped");
	}
}
