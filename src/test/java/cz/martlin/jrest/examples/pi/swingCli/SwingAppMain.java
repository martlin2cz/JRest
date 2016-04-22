package cz.martlin.jrest.examples.pi.swingCli;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.impl.simple.protocol.SimpleGuestProtocolImpl;
import cz.martlin.jrest.impl.simple.reqresps.SimpleRequest;
import cz.martlin.jrest.impl.simple.reqresps.SimpleResponse;

/**
 * The main class for the swing client application. Just simply creates guest
 * and opens the frame with the given guest.
 * 
 * @author martin
 *
 */
public class SwingAppMain {

	private static final int PORT = 31415;

	public static void main(String[] args) {
		SimpleGuestProtocolImpl protocol = new SimpleGuestProtocolImpl(PORT);
		JRestGuest<SimpleRequest, SimpleResponse> guest = new JRestGuest<>(protocol);

		JPiClientFrame frame = new JPiClientFrame(guest);
		frame.setVisible(true);
	}

}
