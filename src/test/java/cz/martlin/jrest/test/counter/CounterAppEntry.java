package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.simple.SimpleWaiterProtocolImpl;
import cz.martlin.jrest.waiter.JRestWaiterShift;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * The main entry for our counter app. Starts Waiter and does some application
 * stuff.
 * 
 * @author martin
 *
 */
public class CounterAppEntry {

	public static final int PORT = 2016;

	public static void main(String[] args) {
		System.out.println("Starting...");

		TheCounterApplication app = new TheCounterApplication();
		RequestHandler processor = new CounterCommandsHandler(app);
		WaiterProtocol protocol = new SimpleWaiterProtocolImpl(PORT, processor);

		// JRestWaiter waiter = new JRestWaiter(protocol);
		// waiter.runWaiter();

		JRestWaiterShift starter = new JRestWaiterShift(protocol);
		starter.startWaiter();

		System.out.println("Started.");

		// do something interresting which takes a time
		app.doSomething();

		try {
			Thread.sleep(10 * 1000);
		} catch (InterruptedException e) {
		}

		app.doSomethingElse();

		System.out.println("Stopping...");

		starter.stopWaiter();

		System.out.println("Stopped.");

	}

}
