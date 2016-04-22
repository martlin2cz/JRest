package cz.martlin.jrest.test.counter;

import java.util.Random;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.protocols.simple.SimpleGuestProtocolImpl;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;

/**
 * The client application of counter. Connects creates guest and sends some
 * commands to waiter (waiter must be started by running {@link CounterAppEntry}
 * ).
 * 
 * @author martin
 *
 */
public class CounterClientApp {

	private static final int COUNT = 20;

	public static void main(String[] args) throws JRestException, InterruptedException {
		SimpleGuestProtocolImpl protocol = new SimpleGuestProtocolImpl(CounterAppEntry.PORT);
		JRestGuest<SimpleRequest, SimpleResponse> guest = new JRestGuest<>(protocol);

		Random rand = new Random();
		for (int i = 0; i < COUNT; i++) {

			String command;
			if (rand.nextInt(3) != 0) {
				command = "increment";
			} else {
				command = "decrement";
			}

			SimpleResponse result = guest.sendRequest(new SimpleRequest(command));
			System.out.println("Command " + command + " invoked, result: " + result + "\n");
			Thread.sleep(1000);
		}

		System.out.println("Done.");
	}

}
