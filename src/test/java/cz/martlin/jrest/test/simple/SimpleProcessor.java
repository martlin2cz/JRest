package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.waiter.CommandProcessor;

/**
 * Simple processor of {@link SimpleMain}. Just prints to console what command
 * recieved and respons acknowlidgement.
 * 
 * @author martin
 *
 */
public class SimpleProcessor implements CommandProcessor {

	public SimpleProcessor() {
	}

	@Override
	public String handleCommand(String command) {
		System.out.println(" >>> Handling: " + command);

		return "Okay, handled " + command;
	}

}
