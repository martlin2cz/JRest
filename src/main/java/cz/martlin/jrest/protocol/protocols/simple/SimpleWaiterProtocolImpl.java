package cz.martlin.jrest.protocol.protocols.simple;

import cz.martlin.jrest.protocol.protocols.dflt.DefaultWaiterProtocolImpl;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * The simple waiter procotol. Uses {@link SimpleJRestProtocolImpl}'s serializer
 * and processors.
 * 
 * @author martin
 *
 */
public class SimpleWaiterProtocolImpl extends DefaultWaiterProtocolImpl {

	/**
	 * Creates simple waiter protocol with given port and (one) processor.
	 * 
	 * @param port
	 * @param processor
	 */
	public SimpleWaiterProtocolImpl(int port, RequestHandler processor) {
		super(port, SimpleJRestProtocolImpl.SERIALIZER, SimpleJRestProtocolImpl.init(processor));
	}

	/**
	 * Creates simple waiter protocol with given port and various processors.
	 * 
	 * @param port
	 * @param processors
	 */
	public SimpleWaiterProtocolImpl(int port, RequestHandler... processors) {
		super(port, SimpleJRestProtocolImpl.SERIALIZER, SimpleJRestProtocolImpl.init(processors));
	}

}
