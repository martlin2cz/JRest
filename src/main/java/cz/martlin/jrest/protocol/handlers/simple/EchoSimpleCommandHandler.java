package cz.martlin.jrest.protocol.handlers.simple;

import java.util.Date;
import java.util.List;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;

/**
 * Implements simple handler such that handles requests with commands
 * {@value #ECHO_COMMAND}. When recieved such command, just simply responds "OK"
 * response with listed arguments as data and current timestamps as metadata.
 * 
 * Also provides method {@link #createRequest(String...)} for simple creating
 * echo requests.
 * 
 * @author martin
 *
 */
public class EchoSimpleCommandHandler extends SingleCommandSimpleRequestHandler {

	public final static String ECHO_COMMAND = "echo";

	public EchoSimpleCommandHandler() {
		super(ECHO_COMMAND);
	}

	@Override
	public SimpleResponse handle(List<String> arguments) throws Exception {
		String data = "Recieved echo command with arguments: " + Tools.listToString(arguments);
		String meta = new Date().toString();

		return SimpleResponse.ok(data, meta);
	}

	/**
	 * Creates echo request with given arguments.
	 * 
	 * @param arguments
	 * @return
	 */
	public static SimpleRequest createRequest(String... arguments) {
		return new SimpleRequest(ECHO_COMMAND, arguments);
	}

}
