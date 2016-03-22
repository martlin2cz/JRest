# JRest

Very simple framework to handle IPC between "server" (the main application) and at least one "client" (the commander(s)) applications. It's similar to Java RMI, but much simplier.  JRest uses pure very simple pure string communication protocol and custom commands processing.

Because it uses servers called "waiter" and clients called "guest" the framework name really mean "restaurant".

## How it works

You have an class `TheCounterApplication` encapsulating some simple counter application's functionality (incrementing and decrementing). But, you need to have counter value stored in memory and have some other application which this counter can increment or decrement. So, you implement custom `CommandProcessor` and specify `CommunicationProtocol`, and wrap counter into `JRestWaiter`. This should look like:

	final CommunicationProtocol PROTOCOL = new CommunicationProtocol(1111); //specify protocol
	TheCounterApplication app = new TheCounterApplication();	//initialize your app here
	CommandProcessor processor = new CounterCommandsProcessor(app);	
	JRestWaiter waiter = new JRestWaiter(PROTOCOL, processor);	//create waiter
	waiter.runWaiting();	//start server
   
Now you can create an `JRestGuest`, a client which connects to counter application via `JRestWaiter` server and call some methods:

	final CommunicationProtocol PROTOCOL = new CommunicationProtocol(1111);	 //specify protocol
	JRestGuest guest = new JRestGuest(CounterAppEntry.PROTOCOL);	//create guest
	String result = guest.sendCommand("increment");	//send some commands
	System.out.println("Command 'increment' invoked, Result: " + result);	//display the result


Take a look into `src/test/java/cz.martlin.jrest.test.counter` to see complete example.
		