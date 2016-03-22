package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.waiter.CommandProcessor;

public class SimpleProcessor implements CommandProcessor {

	public SimpleProcessor() {
	}

	@Override
	public String handleCommand(String command) {
		System.out.println(" >>> Handling: " + command);

		return "Okay, handled " + command;
	}

}
