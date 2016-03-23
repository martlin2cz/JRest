package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.waiter.CommandProcessor;

/**
 * Processor of commands of counter app. Avaiable commands to use are
 * "increment" or "decrement" (in theese cases responds new value of counter),
 * else does nothing and returns "-" as repose.
 * 
 * @author martin
 *
 */
public class CounterCommandsProcessor implements CommandProcessor {

	private final TheCounterApplication app;

	public CounterCommandsProcessor(TheCounterApplication app) {
		this.app = app;
	}

	@Override
	public String handleCommand(String command) {

		if ("increment".equals(command)) {
			app.increment();
		} else if ("decrement".equals(command)) {
			app.decrement();
		} else {
			return "-";
		}

		return Integer.toString(app.getCounter());
	}

}
