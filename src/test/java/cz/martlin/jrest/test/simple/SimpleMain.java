package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.waiter.JRestWaiterStarter;

public class SimpleMain {

	public static void main(String[] args) throws JRestException, InterruptedException {

		CommunicationProtocol protocol = new CommunicationProtocol(1991);
		SimpleProcessor processor = new SimpleProcessor();

		JRestWaiterStarter starter = new JRestWaiterStarter(protocol, processor);
		starter.startWaiting();

		Thread.sleep(100);

		JRestGuest guest = new JRestGuest(protocol);
		guest.sendCommand("Hello!");
		guest.sendCommand("Hi!");

		Thread.sleep(10000);

		guest.sendCommand("Whatever");
		guest.sendCommand(protocol.getExitCommand());

	}

}
