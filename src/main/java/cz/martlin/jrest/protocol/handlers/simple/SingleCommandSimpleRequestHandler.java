package cz.martlin.jrest.protocol.handlers;

import java.util.List;

import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Simple handler such that handles only requests of given command. Every
 * request with different command than the supported one is ignored.
 * 
 * And also implements {@link #initialize(JRestWaiter)} and
 * {@link #finish(JRestWaiter)} as no-op.
 * 
 * @author martin
 *
 */
public abstract class SingleCommandRequestHandler implements RequestHandler {

	private final String command;

	public SingleCommandRequestHandler(String command) {
		this.command = command;
	}

	@Override
	public void initialize(JRestWaiter waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter waiter) throws Exception {
	}

	@Override
	public JRestResponse handle(JRestRequest request) throws Exception {
		if (command.equals(request.getCommand())) {
			return handle(request.getArguments());
		} else {
			return null;
		}
	}

	/**
	 * Handles request with command {@link #command} with given arguments. Could
	 * ignore it and return null.
	 * 
	 * @param arguments
	 * @return
	 * @throws Exception
	 */
	public abstract JRestResponse handle(List<String> arguments) throws Exception;
}
