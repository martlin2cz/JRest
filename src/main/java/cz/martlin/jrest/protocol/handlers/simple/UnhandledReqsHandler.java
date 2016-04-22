package cz.martlin.jrest.protocol.handlers.simple;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
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
public class UnhandledReqsHandler implements RequestHandler<SimpleRequest, SimpleResponse> {

	private final Logger log = LoggerFactory.getLogger(getClass());

	public UnhandledReqsHandler() {
	}

	@Override
	public void initialize(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public SimpleResponse handle(SimpleRequest request) throws Exception {
		return handleQuietly(request);
	}

	/**
	 * Does the handling (but with no declared exception).
	 * 
	 * @param request
	 * @return
	 */
	public SimpleResponse handleQuietly(SimpleRequest request) {

		String message = "Came request with command " + request.getCommand() + " which cannot be handled. "//
				+ "Original arguments: " + Tools.listToString(request.getArguments());

		log.warn(message);

		return SimpleResponse.fatal(message);
	}
}
