package cz.martlin.jrest.impl.jarmil.handlers;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The class performing echoing to be used by {@link JarmilHandler}.
 * 
 * @author martin
 *
 */
public class Echoer {

	public static final String ECHO_METHOD = "echo";
	public static final String CURRENT_TIMESTAMP_METHOD = "currentTimestamp";

	private static final String TEXT = "Hi! This is echo!";

	public Echoer() {
	}

	public String echo() {
		return TEXT;
	}

	public String echo(String message) {
		return message;
	}

	public Calendar currentTimestamp() {
		return new GregorianCalendar();
	}

}
