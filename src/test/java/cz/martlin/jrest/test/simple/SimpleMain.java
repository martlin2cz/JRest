package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.handlers.EchoCommandHandler;
import cz.martlin.jrest.protocol.handlers.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.protocols.simple.SimpleJRestProtocolImpl;
import cz.martlin.jrest.waiter.JRestWaiterShift;

/**
 * The simpliest examples of usage. Both waiter and guest are started in one
 * application (so, the practical pruposes are minimal).
 * 
 * @author martin
 *
 */
public class SimpleMain {

	public static void main(String[] args) throws JRestException, InterruptedException {

		System.out.println("Initializating:");
		SimpleHandler handler = new SimpleHandler();
		SimpleJRestProtocolImpl protocol = new SimpleJRestProtocolImpl(1991, handler);

		JRestWaiterShift starter = new JRestWaiterShift(protocol);
		starter.startWaiter();

		// Thread.sleep(100);

		JRestGuest guest = new JRestGuest(protocol);

		System.out.println("Running some communication:");
		System.out.println();

		JRestRequest req1 = new JRestRequest("Hi!");
		System.out.println(">>> Sending: " + req1);
		System.out.println("<<< Recieved: " + guest.sendCommand(req1));
		System.out.println();

		JRestRequest req2 = new JRestRequest("Hello", "world", "moon", "sun", "mars", "and the whole universe");
		System.out.println(">>> Sending: " + req2);
		System.out.println("<<< Recieved: " + guest.sendCommand(req2));
		System.out.println();

		Thread.sleep(5 * 1000);

		JRestRequest req3 = new JRestRequest("Whatever", Integer.toString(42), "boo");
		System.out.println(">>> Sending: " + req3);
		System.out.println("<<< Recieved: " + guest.sendCommand(req3));
		System.out.println();

		JRestRequest req4 = EchoCommandHandler.createRequest("Hello?", "Do you hear me?");
		System.out.println(">>> Sending: " + req4);
		System.out.println("<<< Recieved: " + guest.sendCommand(req4));
		System.out.println();

		JRestRequest req5 = StopWaiterCommandHandler.createRequest("bye bye");
		System.out.println(">>> Sending: " + req5);
		System.out.println("<<< Recieved: " + guest.sendCommand(req5));
		System.out.println();

		System.out.println("Some communication done.");

		// starter.stopWaiter(); //waiter yet stopped now

	}

}
