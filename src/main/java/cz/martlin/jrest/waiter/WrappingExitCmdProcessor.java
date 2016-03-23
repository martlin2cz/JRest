package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.JRestException;

/**
 * Command processor which
 * <ul>
 * <li>wraps another processor</li>
 * <li>handles and processes waiter exit command</li>
 * </ul>
 * If recieved command is exit command, the waiter exit is invoked, else is
 * command delegated to wrapped processor.
 * 
 * @author martin
 *
 */
public class WrappingExitCmdProcessor implements CommandProcessor {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final CommunicationProtocol protocol;
	private final CommandProcessor wrapped;
	private final JRestWaiter waiter;

	/**
	 * Creates processor with given protocol, with given waiter and wrapping
	 * given wrapped processor.
	 * 
	 * @param protocol
	 * @param wrapped
	 * @param waiter
	 */
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

	/**
	 * Handles exit command.
	 * 
	 * @param command
	 * @return
	 * @throws JRestException
	 */
	private String handleExit(String command) throws JRestException {
		log.debug("Invoked exit command, handling them");

		waiter.stopWaiter();

		return command;
	}
}
