package cz.martlin.jrest.waiter;

import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;

/**
 * Handler of request got by waiter. Each implementation should handle some
 * requests or ignore them - and leave to the next one.
 * 
 * @author martin
 *
 */
public interface RequestHandler<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> {

	/**
	 * If needed initializes handler to work on given waiter.
	 * 
	 * @param waiter
	 * @throws Exception
	 */
	public abstract void initialize(JRestWaiter<RQT, RST> waiter) throws Exception;

	/**
	 * If needed finishes handler's work on given waiter.
	 * 
	 * @param waiter
	 * @throws Exception
	 */
	public abstract void finish(JRestWaiter<RQT, RST> waiter) throws Exception;

	/**
	 * Processes the given request. If this handler does not implement handling
	 * of this command, should return null.
	 * 
	 * @param request
	 * @return response or null
	 * @throws Exception
	 */
	public abstract RST handle(RQT request) throws Exception;
}
