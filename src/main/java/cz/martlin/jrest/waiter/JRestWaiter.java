package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.Interruptable;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.WaiterProtocol;

/**
 * Represents waiter in JRest. The waiter accepts and processes commands from
 * guests.
 * 
 * @author martin
 *
 */
public class JRestWaiter implements Interruptable {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final WaiterProtocol protocol;
	private final RequestsProcessor processor;

	private JRestServer server;
	private boolean interrupted;

	/**
	 * Creates waiter with given communication protocol and processor of
	 * commands.
	 * 
	 * @param protocol
	 * @param processor
	 */
	public JRestWaiter(WaiterProtocol protocol) {
		this.protocol = protocol;
		this.processor = RequestsProcessor.create(protocol);
	}

	@Override
	public void interrupt() {
		this.interrupted = true;
	}

	/**
	 * Runs waiter itself. Creates server and, until is stopped, awaits and
	 * processes commands.
	 */
	public void runWaiter() {
		log.debug("Waiter starting");

		try {
			server = new JRestServer(protocol.getPort());
		} catch (JRestException e) {
			log.error("Cannot start server", e);
			return;
		}

		log.info("Waiter running");

		runServer(processor, server);

		log.debug("Waiter stopping");

		server.finishServer();

		log.info("Waiter stopped");
	}

	/**
	 * Runs given server (awaits and processes (with given processor) commands
	 * until interrupted).
	 * 
	 * @param processor
	 * @param server
	 */
	private void runServer(RequestsProcessor processor, JRestServer server) {
		processor.initialize(this);

		while (!interrupted) {
			server.awaitAndProcess(processor);
		}

		processor.finish(this);
	}

	/**
	 * Stops waiter (marks server as interrupted).
	 */
	public void stopWaiter(String message) {
		log.info("Waiter stop invoked " + //
				((message != null) ? "with message: " + message : ""));

		interrupt();
		server.interrupt();
		WaiterSuicider suicider = new WaiterSuicider(protocol);
		suicider.doSuicide();
	}

}
