package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.Interruptable;
import cz.martlin.jrest.misc.JRestException;

/**
 * Represents waiter in JRest. The waiter accepts and processes commands from
 * guests.
 * 
 * @author martin
 *
 */
public class JRestWaiter implements Interruptable {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final CommunicationProtocol protocol;
	private final CommandProcessor exitProcessor;

	private boolean interrupted;

	/**
	 * Creates waiter with given communication protocol and processor of
	 * commands.
	 * 
	 * @param protocol
	 * @param processor
	 */
	public JRestWaiter(CommunicationProtocol protocol, CommandProcessor processor) {
		this.protocol = protocol;
		this.exitProcessor = new WrappingExitCmdProcessor(protocol, processor, this);
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
		JRestServer server;
		try {
			server = new JRestServer(protocol.getPort());
		} catch (JRestException e) {
			log.error("Cannot start server", e);
			return;
		}

		runServer(exitProcessor, server);

		server.finishServer();
	}

	/**
	 * Runs given server (awaits and processes (with given processor) commands
	 * until interrupted).
	 * 
	 * @param processor
	 * @param server
	 */
	private void runServer(CommandProcessor processor, JRestServer server) {
		while (!interrupted) {
			server.awaitAndProcess(processor);
		}
	}

	/**
	 * Stops waiter (marks server as interrupted).
	 */
	public void stopWaiter() {
		interrupt();
	}

}
