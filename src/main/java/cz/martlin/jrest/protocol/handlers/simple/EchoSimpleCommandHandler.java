package cz.martlin.jrest.protocol.handlers;

import java.util.Date;
import java.util.List;

import cz.martlin.jrest.misc.Tools;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;

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
public class EchoCommandHandler extends SingleCommandRequestHandler {

	public final static String ECHO_COMMAND = "echo";

	public EchoCommandHandler() {
		super(ECHO_COMMAND);
	}

	@Override
	public JRestResponse handle(List<String> arguments) throws Exception {
		String data = "Recieved echo command with arguments: " + Tools.listToString(arguments);
		String meta = new Date().toString();

		return new JRestResponse(ResponseStatus.OK, data, meta);
	}

	/**
	 * Creates echo request with given arguments.
	 * 
	 * @param arguments
	 * @return
	 */
	public static JRestRequest createRequest(String... arguments) {
		return new JRestRequest(ECHO_COMMAND, arguments);
	}

}
