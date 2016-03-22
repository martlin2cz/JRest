package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.waiter.CommandProcessor;

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
