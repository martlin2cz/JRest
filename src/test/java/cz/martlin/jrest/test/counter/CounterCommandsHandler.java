package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Processor of commands of counter app. Avaiable commands to use are
 * "increment" or "decrement" (in theese cases responds new value of counter),
 * else does nothing and returns "-" as repose.
 * 
 * @author martin
 *
 */
public class CounterCommandsHandler implements RequestHandler<SimpleRequest, SimpleResponse> {

	private final TheCounterApplication app;

	public CounterCommandsHandler(TheCounterApplication app) {
		this.app = app;
	}

	@Override
	public void initialize(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public SimpleResponse handle(SimpleRequest request) throws Exception {
		String command = request.getCommand();

		if ("increment".equals(command)) {
			app.increment();
		} else if ("decrement".equals(command)) {
			app.decrement();
		} else {
			return SimpleResponse.error("Unknown command " + command);
		}

		String response = Integer.toString(app.getCounter());
		return SimpleResponse.ok(response);

	}

}
