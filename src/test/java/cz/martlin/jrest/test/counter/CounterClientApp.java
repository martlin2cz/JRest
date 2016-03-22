package cz.martlin.jrest.test.counter;

import java.util.Random;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;

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

			String result = guest.sendCommand(command);
			System.out.println("Command " + command + " invoked, Result: " + result);
			Thread.sleep(1000);
		}

		Thread.sleep(1000);
		guest.stopWaiter();

	}

}
