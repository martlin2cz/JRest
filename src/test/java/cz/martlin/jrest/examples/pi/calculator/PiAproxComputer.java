package cz.martlin.jrest.examples.pi.calculator;

/**
 * Implements slow (simulated by sleeping) approximation of pi with incrementing
 * precision and accessible current state of computation.
 * 
 * @author martin
 *
 */
public class PiAproxComputer {

	private final PiCalculator calculator = new PiCalculator();

	private int currentN;
	private double currentPi;

	private CalculationThread currentThread;
	private boolean stopped;

	public PiAproxComputer() {
		this.stopped = true;
	}

	/**
	 * Returns whether the computation is stopped.
	 * 
	 * @return
	 */
	public boolean isStopped() {
		return stopped;
	}

	/**
	 * Returns whether the computation is running.
	 * 
	 * @return
	 */
	public boolean isRunning() {
		return !stopped;
	}

	/**
	 * Returns current n.
	 * 
	 * @return
	 */
	public int getCurrentN() {
		return currentN;
	}

	/**
	 * Returns current pi.
	 * 
	 * @return
	 */
	public double getCurrentPi() {
		return currentPi;
	}

	/**
	 * Runs the computation. After each iteration is the result stored and
	 * accessible by {@link #getCurrent()}. The computation will stop when the
	 * method {@link #stopTheThread()} is invoked.
	 */
	public void calculate() {
		stopped = false;

		while (!stopped) {
			double value = calculator.calculatePiAprox(currentN);

			synchronized (this) {
				currentPi = value;
				currentN++;
			}

			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
	}

	/**
	 * Runs {@link #calculate()}, but in new thread.
	 */
	public void startInThread() {
		currentThread = new CalculationThread(this);
		currentThread.start();
		stopped = false;
	}

	/**
	 * Stops to computation started by {@link #startInThread()}.
	 */
	public void stopTheThread() {
		stopped = true;
		currentThread.interrupt();
		try {
			currentThread.join();
		} catch (InterruptedException e) {
		}
	}

	@Override
	public String toString() {
		return "PiAproxComputer [currentN=" + currentN + ", currentPi=" + currentPi + ", stopped=" + stopped + "]";
	}

	/**
	 * Thread to run calculation in.
	 * 
	 * @author martin
	 *
	 */
	public static class CalculationThread extends Thread {

		private final PiAproxComputer computer;

		public CalculationThread(PiAproxComputer computer) {
			super("CalculationThread");
			this.computer = computer;
		}

		@Override
		public void run() {
			computer.calculate();
		}
	}
}
