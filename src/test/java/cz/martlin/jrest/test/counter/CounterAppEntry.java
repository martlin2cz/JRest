package cz.martlin.jrest.test.counter;

import cz.martlin.jrest.protocol.protocols.DefaultProtocolImpl;
import cz.martlin.jrest.protocol.serializers.ReqRespSerializer;
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

	private static final TheCounterApplication app = new TheCounterApplication();
	private static final CommandProcessor processor = new CounterCommandsProcessor(app);
	private static final ReqRespSerializer serializer = null; // TODO
	public static final DefaultProtocolImpl PROTOCOL = new DefaultProtocolImpl(1111, "localhost", serializer,
			processor);

	public static void main(String[] args) {

		// JRestWaiter waiter = new JRestWaiter(PROTOCOL, processor);
		// waiter.runWaiter();

		JRestWaiterStarter starter = new JRestWaiterStarter(PROTOCOL);
		starter.startWaiter();

		app.doSomething();
		app.doSomethingElse();

		starter.stopWaiter();

	}

}
