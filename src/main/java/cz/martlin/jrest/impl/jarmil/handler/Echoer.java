package cz.martlin.jrest.impl.jarmil.handler;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * The class performing echoing to be used by {@link JarmilHandler}.
 * 
 * @author martin
 *
 */
public class Echoer implements JarmilTarget {

	public static final String OBJECT_NAME = "echoer";
	public static final String ECHO_METHOD = "echo";
	public static final String CURRENT_TIMESTAMP_METHOD = "currentTimestamp";

	private static final String TEXT = "Hi! This is echo!";
	private static final String DESCRIPTION = "Performs simple echoing. Try invoke " + ECHO_METHOD + " or "
			+ CURRENT_TIMESTAMP_METHOD + ".";

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

	@Override
	public String getJarmilTargetDescription() {
		return DESCRIPTION;
	}

}
