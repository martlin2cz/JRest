package cz.martlin.jrest.test.counter;

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

}
