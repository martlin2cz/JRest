package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.CommunicationProtocol;

public class JRestWaiterStarter {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final JRestWaiter waiter;
	private final DaemonThread daemon;
	private final WaiterRunnable body;

	public JRestWaiterStarter(CommunicationProtocol protocol, CommandProcessor processor) {
		waiter = new JRestWaiter(protocol, processor);
		body = new WaiterRunnable(waiter);
		daemon = new DaemonThread(body);
	}

	public void startWaiting() {

		//daemon.setDaemon(false);// XXX debug!

		daemon.start();
		log.info("Waiter started");
	}

	public void exit() {
		System.out.println("Waiter exiting...");

		waiter.interrupt();
		daemon.interrupt();

		try {
			daemon.join();
		} catch (InterruptedException e) {
		}

		log.info("Waiter stopped");
	}
}
