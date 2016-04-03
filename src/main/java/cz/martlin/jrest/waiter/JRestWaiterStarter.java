package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.protocol.WaiterProtocol;

/**
 * Represents base input entry for {@link JRestWaiter}. Creates and runs waiter,
 * <strong>but in separate thread</strong>.
 * 
 * @author martin
 *
 */
public class JRestWaiterStarter {
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
	public JRestWaiterStarter(WaiterProtocol protocol) {
		waiter = new JRestWaiter(protocol);
		body = new WaiterRunnable(waiter);
		thread = new WaiterThread(body);
	}

	/**
	 * Starts waiter.
	 */
	public void startWaiter() {
		log.debug("Waiter starting..");

		thread.start();

		log.info("Waiter started");
	}

	/**
	 * Stops waiter.
	 */
	public void stopWaiter() {
		log.debug("Waiter stopping..");

		waiter.stopWaiter();
		thread.interrupt();

		try {
			thread.join();
		} catch (InterruptedException e) {
		}

		log.info("Waiter stopped");
	}
}
