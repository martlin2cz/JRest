package cz.martlin.jrest.test.pi.calcApp;

import java.util.List;

import cz.martlin.jrest.protocol.reqresps.simple.ResponseStatus;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.test.pi.calculator.PiAproxComputer;
import cz.martlin.jrest.waiter.JRestWaiter;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Implements handler handling following commands:
 * 
 * <pre>
 * {@value #GIMME_PI_COMMAND} [precision]
 * {@value #GIMME_N_COMMAND}
 * {@value #IS_RUNNING_COMMAND}
 * {@value #COMPLETE_INFO_COMMAND}
 * {@value #START_COMMAND}
 * {@value #STOP_COMMAND}
 * </pre>
 * 
 * @author martin
 *
 */
public class PiComputerHandler implements RequestHandler<SimpleRequest, SimpleResponse> {

	public static final String GIMME_PI_COMMAND = "gimme-current-pi";
	public static final String GIMME_N_COMMAND = "gimme-current-n";
	public static final String IS_RUNNING_COMMAND = "is-running";
	public static final String COMPLETE_INFO_COMMAND = "gimme-complete-info";
	public static final String START_COMMAND = "start-computing";
	public static final String STOP_COMMAND = "stop-computing";

	private final PiAproxComputer computer;

	public PiComputerHandler(PiAproxComputer computer) {
		this.computer = computer;
	}

	@Override
	public void initialize(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public void finish(JRestWaiter<SimpleRequest, SimpleResponse> waiter) throws Exception {
	}

	@Override
	public SimpleResponse handle(SimpleRequest request) throws Exception {
		if (GIMME_PI_COMMAND.equals(request.getCommand())) {
			return handleGimmePi(request.getArguments());
		}

		if (GIMME_N_COMMAND.equals(request.getCommand())) {
			return handleGimmeN();
		}

		if (IS_RUNNING_COMMAND.equals(request.getCommand())) {
			return handleIsRunning();
		}

		if (COMPLETE_INFO_COMMAND.equals(request.getCommand())) {
			return handleCompleteInfo();
		}

		if (START_COMMAND.equals(request.getCommand())) {
			return handleStart();
		}

		if (STOP_COMMAND.equals(request.getCommand())) {
			return handleStop();
		}

		return null;
	}

	/**
	 * Handles the command gimme current pi.
	 * 
	 * @param arguments
	 * @return
	 */
	private SimpleResponse handleGimmePi(List<String> arguments) {
		int size = 8;

		ResponseStatus status = ResponseStatus.OK;
		String meta = getStatus();

		if (!arguments.isEmpty()) {
			String sizeStr = arguments.get(0);
			try {
				size = Integer.parseInt(sizeStr);
			} catch (Exception e) {
				status = ResponseStatus.ERROR;
				meta = "Invalid size: " + sizeStr;
			}
		}

		double value = computer.getCurrentPi();
		String data = String.format("%1." + size + "f", value);

		return new SimpleResponse(status, data, meta);
	}

	/**
	 * Handles "gimme current n" command.
	 * 
	 * @return
	 */
	private SimpleResponse handleGimmeN() {
		String meta = getStatus();

		int value = computer.getCurrentN();
		String data = Integer.toString(value);

		return SimpleResponse.ok(data, meta);
	}

	/**
	 * Handles "is running" command.
	 * 
	 * @return
	 */
	private SimpleResponse handleIsRunning() {
		String data = computer.isRunning() ? "yes" : "no";
		return SimpleResponse.ok(data);
	}

	/**
	 * Handles "complete info" command.
	 * 
	 * @return
	 */
	private SimpleResponse handleCompleteInfo() {
		synchronized (computer) {
			String data = "[" + //
					"'isRunning': " + computer.isRunning() + ", " + //
					"'currentN': " + computer.getCurrentN() + ", " + //
					"'currentPi': " + computer.getCurrentPi() + //
					"]";
			return SimpleResponse.ok(data);
		}

	}

	/**
	 * Handles stop of computing.
	 * 
	 * @return
	 */
	private SimpleResponse handleStop() {
		String meta = getStatus();

		computer.stopTheThread();

		String data = getStatus();

		return SimpleResponse.ok(data, meta);
	}

	/**
	 * Handles start of computing.
	 * 
	 * @return
	 */
	private SimpleResponse handleStart() {
		String meta = getStatus();

		computer.startInThread();

		String data = getStatus();

		return SimpleResponse.ok(data, meta);
	}

	/**
	 * Returns string corresponding to the status of computer (stopped or
	 * running).
	 * 
	 * @return
	 */
	private String getStatus() {
		String status;

		if (computer.isStopped()) {
			status = "stopped";
		} else {
			status = "running";
		}

		return status;
	}

}
