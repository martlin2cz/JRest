package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.waiter.CommandProcessor;
import cz.martlin.jrest.waiter.JRestWaiter;

public class CounterAppEntry {

	public static final CommunicationProtocol PROTOCOL = new CommunicationProtocol(1111);

	public static void main(String[] args) {
		TheCounterApplication app = new TheCounterApplication();

		CommandProcessor processor = new CounterCommandsProcessor(app);

		JRestWaiter waiter = new JRestWaiter(PROTOCOL, processor);
		waiter.runWaiting();
	}

}
