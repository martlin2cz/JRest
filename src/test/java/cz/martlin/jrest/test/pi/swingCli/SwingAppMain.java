package cz.martlin.jrest.test.pi.swingCli;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.protocols.simple.SimpleGuestProtocolImpl;

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
		GuestProtocol protocol = new SimpleGuestProtocolImpl(PORT);
		JRestGuest guest = new JRestGuest(protocol);

		JPiClientFrame frame = new JPiClientFrame(guest);
		frame.setVisible(true);
	}

}
