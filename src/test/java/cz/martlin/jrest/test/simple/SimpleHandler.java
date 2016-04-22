package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.handlers.simple.EchoSimpleCommandHandler;
import cz.martlin.jrest.protocol.handlers.simple.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Simple processor of {@link SimpleMain}. Just prints to console what command
 * recieved and respons acknowlidgement.
 * 
 * @author martin
 *
 */
public class SimpleHandler implements RequestHandler<SimpleRequest, SimpleResponse> {

	public SimpleHandler() {
	}

	@Override
	public void initialize(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public SimpleResponse handle(SimpleRequest request) throws Exception {
		if (StopWaiterCommandHandler.STOP_WAITER_COMMAND.equals(request.getCommand())
				|| EchoSimpleCommandHandler.ECHO_COMMAND.equals(request.getCommand())) {
			return null;
		}

		System.err.println(" === Handling: " + request.getCommand() + " with arguments: "
				+ Tools.listToString(request.getArguments()));

		return SimpleResponse.ok("Okay, handled " + request.getCommand());
	}

}
