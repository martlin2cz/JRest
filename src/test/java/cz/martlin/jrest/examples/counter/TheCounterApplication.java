package cz.martlin.jrest.examples.counter;

/**
 * Our simple application.
 * 
 * @author martin
 *
 */
public class TheCounterApplication {

	private int counter;

	public TheCounterApplication() {
	}

	public int getCounter() {
		return counter;
	}

	public void increment() {
		counter++;
	}

	public void decrement() {
		counter--;
	}

	@Override
	public String toString() {
		return "TheCounterApplication [counter=" + counter + "]";
	}

	public void doSomething() {
		System.out.println("The app is running and now will wait next 20 seconds");
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
		}
		System.out.println("The app is still running, the counter is now: " + counter);
	}

	public void doSomethingElse() {
		System.out.println("The app will now do something which takes exactly two seconds to compute.");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		System.out.println("It has computed something, the counter is now " + counter);
	}

}
