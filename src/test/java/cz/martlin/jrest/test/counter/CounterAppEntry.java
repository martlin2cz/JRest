package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.waiter.CommandProcessor;
import cz.martlin.jrest.waiter.JRestWaiterStarter;

/**
 * The main entry for our counter app. Starts Waiter and does some application
 * stuff. 
 * 
 * @author martin
 *
 */
public class CounterAppEntry {

	public static final CommunicationProtocol PROTOCOL = new CommunicationProtocol(1111);

	public static void main(String[] args) {
		TheCounterApplication app = new TheCounterApplication();

		CommandProcessor processor = new CounterCommandsProcessor(app);

		// JRestWaiter waiter = new JRestWaiter(PROTOCOL, processor);
		// waiter.runWaiter();

		JRestWaiterStarter starter = new JRestWaiterStarter(PROTOCOL, processor);
		starter.startWaiter();

		app.doSomething();
		app.doSomethingElse();

		starter.stopWaiter();

	}

}
