package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.JRestException;

public class WrappingExitCmdProcessor implements CommandProcessor {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final CommunicationProtocol protocol;
	private final CommandProcessor wrapped;
	private final JRestWaiter waiter;

	public WrappingExitCmdProcessor(CommunicationProtocol protocol, CommandProcessor wrapped, JRestWaiter waiter) {
		this.protocol = protocol;
		this.wrapped = wrapped;
		this.waiter = waiter;
	}

	@Override
	public String handleCommand(String command) throws Exception {
		if (protocol.getExitCommand().equals(command)) {
			return handleExit(command);
		} else {
			return wrapped.handleCommand(command);
		}
	}

	private String handleExit(String command) throws JRestException {
		log.debug("Invoked exit command, handling them");

		waiter.interrupt();

		return command;
	}
}
