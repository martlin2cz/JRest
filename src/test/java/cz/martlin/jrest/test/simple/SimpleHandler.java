package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.handlers.EchoCommandHandler;
import cz.martlin.jrest.protocol.handlers.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Simple processor of {@link SimpleMain}. Just prints to console what command
 * recieved and respons acknowlidgement.
 * 
 * @author martin
 *
 */
public class SimpleHandler implements RequestHandler {

	public SimpleHandler() {
	}

	@Override
	public void initialize(JRestWaiter waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter waiter) throws Exception {
	}

	@Override
	public JRestResponse handle(JRestRequest request) throws Exception {
		if (StopWaiterCommandHandler.STOP_WAITER_COMMAND.equals(request.getCommand())
				|| EchoCommandHandler.ECHO_COMMAND.equals(request.getCommand())) {
			return null;
		}

		System.err.println(" === Handling: " + request.getCommand() + " with arguments: "
				+ Tools.listToString(request.getArguments()));

		return JRestResponse.ok("Okay, handled " + request.getCommand());
	}

}
