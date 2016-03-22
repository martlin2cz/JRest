package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.Interruptable;
import cz.martlin.jrest.misc.JRestException;

public class JRestWaiter implements Interruptable {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final CommunicationProtocol protocol;
	private final CommandProcessor exitProcessor;

	private boolean interrupted;

	public JRestWaiter(CommunicationProtocol protocol, CommandProcessor processor) {
		this.protocol = protocol;
		this.exitProcessor = new WrappingExitCmdProcessor(protocol, processor, this);
	}

	public void runWaiting() {
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

	private void runServer(CommandProcessor processor, JRestServer server) {
		while (!interrupted) {
			server.awaitAndProcess(processor);
		}
	}

	@Override
	public void interrupt() {
		this.interrupted = true;
	}

}
