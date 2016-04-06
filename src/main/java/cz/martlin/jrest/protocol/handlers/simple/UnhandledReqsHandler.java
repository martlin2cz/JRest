package cz.martlin.jrest.protocol.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * A special handler which is not recommended to normal use. Handles each
 * request (i.e. no one is ignored) and the handle procedure logs warning on
 * waiter and responds "FATAL" with described request as metadata.
 * 
 * @author martin
 *
 */
public class UnhandledReqsHandler implements RequestHandler {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public UnhandledReqsHandler() {
	}

	@Override
	public void initialize(JRestWaiter waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter waiter) throws Exception {
	}

	@Override
	public JRestResponse handle(JRestRequest request) throws Exception {
		return handleQuietly(request);
	}

	/**
	 * Does the handling (but with no declared exception).
	 * 
	 * @param request
	 * @return
	 */
	public JRestResponse handleQuietly(JRestRequest request) {

		String message = "Came request with command " + request.getCommand() + " which cannot be handled. "//
				+ "Original arguments: " + Tools.listToString(request.getArguments());

		log.warn(message);

		return JRestResponse.fatal(message);
	}
}
