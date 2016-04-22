package cz.martlin.jrest.examples.pi.consoleCli;

import cz.martlin.jrest.examples.pi.calcApp.PiComputerHandler;
import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.impl.simple.protocol.SimpleGuestProtocolImpl;
import cz.martlin.jrest.impl.simple.reqresps.SimpleRequest;
import cz.martlin.jrest.impl.simple.reqresps.SimpleResponse;
import cz.martlin.jrest.misc.JRestException;

/**
 * Very simple console client. Just sends some requests and prints to stderr
 * responses.
 * 
 * @author martin
 *
 */
public class ConsoleClient {

	private static final int PORT = 31415;

	public static void main(String[] args) throws JRestException, InterruptedException {

		SimpleGuestProtocolImpl protocol = new SimpleGuestProtocolImpl(PORT);
		JRestGuest<SimpleRequest, SimpleResponse> guest = new JRestGuest<>(protocol);

		System.err.println("Querying:");

		sendAndPrintResponse(guest, "Current pi value", PiComputerHandler.GIMME_PI_COMMAND);
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Start", PiComputerHandler.START_COMMAND);
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Current pi value", PiComputerHandler.GIMME_PI_COMMAND);
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Current n value", PiComputerHandler.GIMME_N_COMMAND);
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Current pi value", PiComputerHandler.GIMME_PI_COMMAND, Integer.toString(20));
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Current n value", PiComputerHandler.GIMME_N_COMMAND);
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Current status", PiComputerHandler.COMPLETE_INFO_COMMAND);
		Thread.sleep(1000);

		sendAndPrintResponse(guest, "Stop", PiComputerHandler.STOP_COMMAND);
		Thread.sleep(1000);

		System.err.println("Done.");

	}

	private static void sendAndPrintResponse(JRestGuest<SimpleRequest, SimpleResponse> guest, String desc,
			String command, String... arguments) throws JRestException {

		SimpleRequest request = new SimpleRequest(command, arguments);
		SimpleResponse response = guest.sendRequest(request);

		System.err.println(desc + ": \t " + response.getData() + //
				" \t (complete response: " + response + ")");
	}

}
