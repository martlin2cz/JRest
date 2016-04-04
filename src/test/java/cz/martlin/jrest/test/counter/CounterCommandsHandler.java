package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.JRestResponse;
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
public class CounterCommandsHandler implements RequestHandler {

	private final TheCounterApplication app;

	public CounterCommandsHandler(TheCounterApplication app) {
		this.app = app;
	}

	@Override
	public void initialize(JRestWaiter waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter waiter) throws Exception {
	}

	@Override
	public JRestResponse handle(JRestRequest request) throws Exception {
		String command = request.getCommand();

		if ("increment".equals(command)) {
			app.increment();
		} else if ("decrement".equals(command)) {
			app.decrement();
		} else {
			return JRestResponse.error("", "Unknown command " + command);
		}

		String response = Integer.toString(app.getCounter());
		return JRestResponse.ok(response);

	}

}
