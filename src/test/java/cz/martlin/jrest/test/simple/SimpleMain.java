package cz.martlin.jrest.test.simple;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.handlers.simple.EchoSimpleCommandHandler;
import cz.martlin.jrest.protocol.handlers.simple.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.protocols.simple.SimpleJRestProtocolImpl;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
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

		JRestWaiterShift<SimpleRequest, SimpleResponse> starter = //
		new JRestWaiterShift<SimpleRequest, SimpleResponse>(protocol);

		starter.startWaiter();

		// Thread.sleep(100);

		JRestGuest<SimpleRequest, SimpleResponse> guest = new JRestGuest<SimpleRequest, SimpleResponse>(protocol);

		System.out.println("Running some communication:");
		System.out.println();

		SimpleRequest req1 = new SimpleRequest("Hi!");
		System.out.println(">>> Sending: " + req1);
		System.out.println("<<< Recieved: " + guest.sendRequest(req1));
		System.out.println();

		SimpleRequest req2 = new SimpleRequest("Hello", "world", "moon", "sun", "mars", "and the whole universe");
		System.out.println(">>> Sending: " + req2);
		System.out.println("<<< Recieved: " + guest.sendRequest(req2));
		System.out.println();

		Thread.sleep(5 * 1000);

		SimpleRequest req3 = new SimpleRequest("Whatever", Integer.toString(42), "boo");
		System.out.println(">>> Sending: " + req3);
		System.out.println("<<< Recieved: " + guest.sendRequest(req3));
		System.out.println();

		SimpleRequest req4 = EchoSimpleCommandHandler.createRequest("Hello?", "Do you hear me?");
		System.out.println(">>> Sending: " + req4);
		System.out.println("<<< Recieved: " + guest.sendRequest(req4));
		System.out.println();

		SimpleRequest req5 = StopWaiterCommandHandler.createRequest("bye bye");
		System.out.println(">>> Sending: " + req5);
		System.out.println("<<< Recieved: " + guest.sendRequest(req5));
		System.out.println();

		System.out.println("Some communication done.");

		// starter.stopWaiter(); //waiter yet stopped now

	}

}
