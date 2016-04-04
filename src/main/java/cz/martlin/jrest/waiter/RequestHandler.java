package cz.martlin.jrest.waiter;

import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.JRestResponse;

/**
 * Processor of request got by waiter. Each implementation should handle given
 * command or ignore them - and leave to the next one.
 * 
 * @author martin
 *
 */
public interface RequestHandler {

	/**
	 * If needed initializes handler to work on given waiter.
	 * 
	 * @param waiter
	 * @throws Exception
	 */
	public abstract void initialize(JRestWaiter waiter) throws Exception;

	/**
	 * If needed finishes handler's work on given waiter.
	 * 
	 * @param waiter
	 * @throws Exception
	 */
	public abstract void finish(JRestWaiter waiter) throws Exception;

	/**
	 * Processes the given request. If this processor does not implement
	 * handling of this command, should return null.
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public abstract JRestResponse handle(JRestRequest request) throws Exception;
}
