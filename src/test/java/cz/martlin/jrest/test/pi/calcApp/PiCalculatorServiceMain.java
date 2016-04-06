package cz.martlin.jrest.test.pi.calcApp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.handlers.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.protocols.simple.SimpleWaiterProtocolImpl;
import cz.martlin.jrest.test.pi.calculator.PiAproxComputer;
import cz.martlin.jrest.waiter.JRestWaiterShift;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * The main application ("computing service app") for computing pi.
 * 
 * @author martin
 *
 */
public class PiCalculatorServiceMain {
	private static final Logger log = LoggerFactory.getLogger(PiCalculatorServiceMain.class);

	private static final int PORT = 31415;

	public static void main(String[] args) {
		log.debug("Preparing...");

		PiAproxComputer computer = new PiAproxComputer();

		log.debug("Starting waiter...");

		runWaiter(computer);

		log.debug("Waiter running. Hit Ctrl+C or use " //
				+ "'echo " + StopWaiterCommandHandler.STOP_WAITER_COMMAND + " \"telnet interrupt\" | telnet localhost "
				+ PORT + "'");

		// log.info("Computation running...");
		// computer.calculate();
		// log.debug("Computation stopped");

		// Thread.sleep(10 * 1000);
		// shift.stopWaiter();

		log.debug("Stopped.");
	}

	/**
	 * For given computer starts its waiter.
	 * 
	 * @param computer
	 */
	private static void runWaiter(PiAproxComputer computer) {
		RequestHandler handler = new PiComputerHandler(computer);
		WaiterProtocol protocol = new SimpleWaiterProtocolImpl(PORT, handler);
		JRestWaiterShift shift = new JRestWaiterShift(protocol);
		shift.startWaiter();
	}

}
