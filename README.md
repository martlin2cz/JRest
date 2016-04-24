# JRest

## Abstract

JRest is framework which simulates something like restaurant. Also, JRest is the IPC _(inter-process communication)_ (or, if you want, RMI _(remote method invocation)_) java framework.

The application using the JRest contains three parts. A waiter (takes the role of server), a guest(s) (takes the role of client(s)) and the communication protocol. The guests sends requests to their waiter, he handles and processes them and sends them back responses. The protocol specifies port of communication, encoding of requests and responses and handling of the requests.

Since version 1.2 is JRest more abstract and, in fact, just set of interfaces. Also this project contains two implementations.  

## How it works
The following diagram shows the principle of the whole framework:
```
[Client app class]
        |
        | (JRest request)
        V
   [JRestGuest]
        |
        | (serialized request)
        V
  [JRestWaiter]
        |
        | (JRest request)
        V
 [RequestHandler]           
        |
        | (method call)
        V
[Server app class]
        |
        | (returning value)
        V 
 [RequestHandler]
        |
        | (JRest response)
        V
   [JRestWaiter]
        |
        | (serialize request)
        V             
    [JRestGuest]
        |
        | (JRest request)
        V             
[Client app class]
```

## Usage 
Take a look to package `cz.martlin.jrest.examples` in `src/test/java` to see more examples. Also, take a look to file README-previous-version.md to see more complex (but outdated example application's description).

Since the version 1.3 thing became much simplier. So, lets start!

Imagine you are implementing some "commons" service. A simple library which should run as service and provide some common things, like generating random numbers of recieving current weather situation.

This is the typical usage of JRest (and, in fact, the reason, why I have JRest created). How it will look like?

Firstly, assume we have a class `CommonsService` like this:

```java
public class CommonsService {
  private final Random rand = new Random();

  public int getRandomNumber(Integer max) {
     return rand.nextInt(max);
  }
  
  //some other usefull methods...

}
```
Since we will use the new Jarmil implementation, we need to make our service let implement the `JarmilTarget` interface. So, change the class header to:
```
public class CommonsService implements JarmilTarget {
```

Now we will make this executable. That means we will create a main class, called `CommonsServiceApp` which will look like:
```java
public class CommonsServiceApp {
  public static final int PORT = 2016;
  public static final String NAME = "commons";

  public static void main(String[] args) {
    System.out.println("Starting");

    final CommonsService service = new CommonsService();  //create our service object
	final TargetOnWaiter target = ObjectOnWaiterTarget.create(NAME, service);  //specify how to invoke it
	final JarmilWaiterProtocol protocol = JarmilWaiterProtocol.createSingle(PORT, target);  //create protocol

	JarmilWaiterShift shift = new JarmilWaiterShift(protocol);
    
    System.out.println("Started");
  }
}
```

Compile and run it. If the waiter correctly starts, you can try to send commands via `telnet` (see "Telnet" chapter). Okay, waiter is ready and working. Now lets make a simple console client, class `CommonSimpleClientApp`, with following main method:
```java
public class CommonSimpleClientApp {
  public static void main(String[] args) throws JRestException {
    
    TargetOnGuest target = ObjectOnGuestTarget.create(CommonsServiceApp.NAME);
    JarmilGuestProtocol protocol = new JarmilGuestProtocol(CommonsServiceApp.PORT);
    SingleJarmilGuest guest = new SingleJarmilGuest(target, protocol);
    
	System.out.println("Random number: " + guest.invoke("getRandomNumber", 10));
  }
}	
```
The code does as expected. Sends request to call method on the instance of `CommonsService` created in the `CommonsServiceApp`. Then recieves result and prints.


#Features

## Goals
The goal of JRest was to implement simple & lightweight (in many cases, from usage, over configuration to openness) but scaleable and customizable framework. JRest doesn't want to replace RMI or something like that. It is made to send simple commands between applications, with minimal amount of data ("I just wanted to tell you ...").

The whole communication process is constructed as complex of interfaces, with their default implementations. Due the process transparency is JRest open to implement custom components (requests, responses, serializers, handlers, protocols, waiters and guests)! 

## Implementations
JRest currently comes with two implementation. The old, low lewel and not really prefered Simple and the modern, new and comfortable Jarmil. 

Simple implementation uses philosophy simillar to unix shell (request contains command name and arguments, and response has status ("error code"), data part ("stdout") and metadata part ("stderr"). The main disadvantage of this implementation is the need of explicit implementation of commands handler. Yea, it is powerfull, but ... makes JRest complicated (see `counter`, `pi` and `simple` examples).

So, I created new implementation. The new implementation is call Jarmil (**Ja**va **RMI** **l**ike) and is simillar to RMI. See the example above. 

## JRest and Telnet
Sometimes you don't need to use client to request an waiter. If you know the communication protocol, you can use `telnet` to communicate. In the previous example, you could use something like:
```bash
echo "object commons getRandomNumber 10" | telnet localhost 2016
``` 
to generate random number. Unfortunatelly, the `telnet` command does not display the response output. So, to verify, you should add some logging into `CommonsService`.


## Echo and StopWaiter
Both old Simple and new Jarmil implementation has two featuring handlers, the "echo" and "StopWaiter". Echo just simply responds given input, so it is good for testing and debugging. The StopWaiter allows to stop the waiter instance "from the outside world".

When using Jarmil, the each Jarmil target has to implement method `getJarmilTargetDescription` which should return simple description about the supported methods. 

## Security note
Notice that JRest itself does not guarantee the security of the application. When using, be careful to have blocked (i.e. via firewall) ports used by your JRest application from the outside world. Similarly, when you have to run JRest remotely, always think about the security (and use some authorisation token for instance). But remember, the communication is still unsecured, so anyone can catch the socket and read the authorisation data.

The new Jarmil implementation makes security more riskfull. Each guest can invoke **all** the (public) methods of the jarmil target. Althought, it is secured by `JarmilTarget` interface (guest can invoke only methods on classes implementing `JarmilTarget` interface) and environment (only classes provided in waiter's environment can have they're methods invoked). But to avoid invoking on some particular method, you just need to make it private or - simply - comment it out.   

So, again, be careful and never let JRest waiter to do something critical.  

## Anything?

Anything else? No, just **enyoy your meal**!