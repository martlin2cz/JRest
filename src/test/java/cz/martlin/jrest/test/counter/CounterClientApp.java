package cz.martlin.jrest.test.counter;

import java.util.Random;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.JRestResponse;

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
		JRestGuest guest = new JRestGuest(CounterAppEntry.PROTOCOL);
		Random rand = new Random();

		for (int i = 0; i < COUNT; i++) {

			String command;
			if (rand.nextInt(3) != 0) {
				command = "increment";
			} else {
				command = "decrement";
			}

			JRestResponse result = guest.sendCommand(new JRestRequest(command));
			System.out.println("Command " + command + " invoked, Result: " + result);
			Thread.sleep(1000);
		}

		Thread.sleep(1000);
		//TODO guest.stopWaiter();

	}

}
