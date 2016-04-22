package cz.martlin.jrest.protocol.handlers.simple;

import java.util.List;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.waiter.JRestWaiter;

/**
 * Implements simple handler (handling {@value #STOP_WAITER_COMMAND} command)
 * such that when invoked, stops the waiter. The request should contain no
 * arguments or exactly one - the aditional message (to be logged), reason why
 * have been waiter stopped (or something like that). In that case responds
 * instead of "OK", "WARN".
 * 
 * Also provides methods to create waiter-stop requests (
 * {@link #createRequest()} and {@link #createRequest(String)}).
 * 
 * @author martin
 *
 */
public class StopWaiterCommandHandler extends SingleCommandSimpleRequestHandler {

	public static final String STOP_WAITER_COMMAND = "stop-waiter";

	private JRestWaiter<SimpleRequest, SimpleResponse> waiter;

	public StopWaiterCommandHandler() {
		super(STOP_WAITER_COMMAND);
	}

	@Override
	public void initialize(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
		this.waiter = waiter;
	}

	@Override
	public void finish(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
		this.waiter = null;
	}

	@Override
	public SimpleResponse handle(List<String> arguments) throws Exception {
		if (arguments.size() == 0) {
			return stopWaiter(null, null);
		} else if (arguments.size() == 1) {
			return stopWaiter(arguments.get(0), null);
		} else {
			return stopWaiter(arguments.get(0), arguments);
		}
	}

	private SimpleResponse stopWaiter(String messageOrNull, List<String> extraArgsOrNull) {
		waiter.stopWaiter(messageOrNull);

		String data = "Waiter stopped";

		if (extraArgsOrNull == null) {
			return SimpleResponse.ok(data);
		} else {
			return SimpleResponse.warn(data,
					"Excepted 0 or 1 arguments, given: " + Tools.listToString(extraArgsOrNull));
		}
	}

	/**
	 * Creates stop-waiter request with no message.
	 * 
	 * @return
	 */
	public static SimpleRequest createRequest() {
		return new SimpleRequest(STOP_WAITER_COMMAND);
	}

	/**
	 * Creates stop-waiter request with given message.
	 * 
	 * @param message
	 * @return
	 */
	public static SimpleRequest createRequest(String message) {
		return new SimpleRequest(STOP_WAITER_COMMAND, message);
	}

}
