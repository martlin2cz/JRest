# JRest

## Abstract

JRest is framework which simulates something like restaurant. Also, JRest is the IPC _(inter-process communication)_ (or, if you want, RMI _(remote method invocation)_) java framework.

The application using the JRest contains three parts. A waiter (takes the role of server), a guest(s) (takes the role of client(s)) and the communication protocol. The guests sends requests to their waiter, he handles and processes them and sends them back responses. The protocol specifies port of communication, encoding of requests and responses and handling of the requests.

## How it works
The following diagram shows the principle of the whole framework:
```
                                                    !                                 JRestRequest
[Client app] --------->  [JRestGuest instance]  ---------> [JRestWaiter instance] ---.  instance
			JRestRequest                         network                             |   
              instance                              !                                V
			                                        !                 [CommandHandler instance]  <-----------> ["Server" app]
                                                    !                                |
                                                    !                                |  
[Client app] <---------- [JRestGuest instance] <---------- [JRestWaiter instance] <-'  JRestResponse
            JRestResponse                        network                                  instance
              instance                              !
```

## Usage 

You have a service computing approximations of number pi. The computing takes a long time and the result is little by little closer to real pi value. So, you decide to make a simple "service" app, which runs the computation and on demand gives back the currently computed value. In addition, you would like to add option to start and stop computation by hand.

This service, class `PiAproxComputer`, will have following methods:
 - `startComputing()`
 - `stopComputing()`
 - `getCurrentPi()`

Once is ready, you will create a requests handler, a class `PiComputerHandler` like this:
```java
public class PiComputerHandler implements RequestHandler {
	private final PiAproxComputer computer;

	public PiComputerHandler(PiAproxComputer computer) {
		this.computer = computer;
	}

	@Override
	public void initialize(JRestWaiter waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter waiter) throws Exception {
	}

	@Override
	public JRestResponse handle(JRestRequest request) throws Exception {
	
		if ("start-computing".equals(request.getCommand())) {
			computer.startComputing();
			return JRestResponse.ok("Started");
		}

		if ("stop-computing".equals(request.getCommand())) {
			computer.stopComputing();
			return JRestResponse.ok("Stopped");
		}

		if ("get-current-pi".equals(request.getCommand())) {
			String format = request.getArguments().get(0);
			double d = computer.getCurrentPi();
			String s = String.format(format, d);
			return JRestResponse.ok(s);
		}

		return null;
	}
}
```
Now, when you have prepared the handler, you can set up the whole waiter. Create an main class called `PiCalculatorServiceMain` and in the main method type following:
```java
	final int port = 31415;
	PiAproxComputer computer = new PiAproxComputer();
	
	RequestHandler handler = new PiComputerHandler(computer);
	WaiterProtocol protocol = new SimpleWaiterProtocolImpl(port, handler);
	JRestWaiterShift shift = new JRestWaiterShift(protocol);
	
	shift.startWaiter();
```
If the waiter correctly starts, you can try to send commands via `telnet` (see "Telnet" chapter). Okay, waiter is ready and working. Now lets make a simple console client, class `ConsoleClient`, with following content in main method:
```java
	final int port = 31415;
	
	GuestProtocol protocol = new SimpleGuestProtocolImpl(port);
	JRestGuest guest = new JRestGuest(protocol);
	
	JRestRequest startReq = new JRestRequest("start-computing");
	JRestResponse startResp = guest.sendRequest(startReq);
	
	Thread.sleep(10 * 1000);
	
	JRestRequest getReq = new JRestRequest("get-current-pi", "%1.4f");
	JRestResponse getResp = guest.sendRequest(startReq);
	System.out.println("Current value of pi is: " + getResp.getData());
	
	Thread.sleep(10 * 1000);
	
	JRestRequest stopReq = new JRestRequest("stop-computing");
	JRestResponse stopResp = guest.sendRequest(stopReq);
```
The code does as expected. Sends request to start computing, waits 10 seconds, queries currently computed value, waits next 10 seconds and then stops computing. You would have to handle some exceptions, but it should be no problem.

**Note: ** previous example usage is simplified application from package `cz.martlin.jrest.test.pi` in `test` directory. Don't forget to take a look at another two example applications, `cz.martlin.jrest.test.simple` and `cz.martlin.jrest.test.counter`.

#Features

## Goals
The goal of JRest was to implement simple & lightweight (in many cases, from usage, over configuration to openness) framework. JRest doesn't want to replace RMI or something like that. It is made to send simple commands between applications, with minimal amount of data ("I just wanted to tell you ...").

The whole communication process is constructed as complex of interfaces, with their default implementations. Due the process transparency is JRest open to implement custom components! 

## JRest and Telnet
Sometimes you don't need to use client to request an waiter. If you know the communication protocol, you can use `telnet` to communicate. In the previous example, you could use something like:
```bash
echo "start-computing" | telnet localhost 31415
echo "stop-computing" | telnet localhost 31415
``` 
to start and stop the computing.

## Serializers
The JRest comes with the "simple" request/response serializer implementation, class `ShellLikeSerializer` (the `BasicLinedSerializer` also can be used, but see javadoc to see why not). Feel free to implement custom! For instance, send requests and responses in JSON or xml instead of basic "shell like format".

## Echo and StopWaiter handlers
When using simple protocol (`SimpleWaiterProtocolImpl` or `SimpleJRestProtocolImpl`) there are automatically added two additional handlers. The first one is echo handler, class `EchoCommandHandler`, which handles commands with name echo and simply responds the sent arguments back. It's made to use in diagnostics and testing/debugging. The second one is handler, which performs stopping of the waiter (class `StopWaiterCommandHandler`). 

Just remind that the waiter (if runned in shift) can be stopped "by hand" (by calling of method) directly from the waiter itself.

## Anything?

Anything else? No, just **enyoy your meal**!



