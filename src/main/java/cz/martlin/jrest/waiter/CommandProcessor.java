package cz.martlin.jrest.waiter;

/**
 * Processor of commands got by waiter.
 * 
 * @author martin
 *
 */
public interface CommandProcessor {

	/**
	 * Processes command. Should do something in application and return response
	 * for guest.
	 * 
	 * @param command
	 * @return
	 * @throws Exception
	 */
	public abstract String handleCommand(String command) throws Exception;
}
