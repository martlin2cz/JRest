package cz.martlin.jrest.impl.simple.protocol;

import cz.martlin.jrest.impl.dflt.protocol.DefaultWaiterProtocolImpl;
import cz.martlin.jrest.impl.simple.reqresps.SimpleRequest;
import cz.martlin.jrest.impl.simple.reqresps.SimpleResponse;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * The simple waiter procotol. Uses {@link SimpleJRestProtocolImpl}'s serializer
 * and processors.
 * 
 * @author martin
 *
 */
public class SimpleWaiterProtocolImpl extends DefaultWaiterProtocolImpl<SimpleRequest, SimpleResponse> {

	/**
	 * Creates simple waiter protocol with given port and (one) processor.
	 * 
	 * @param port
	 * @param processor
	 */
	public SimpleWaiterProtocolImpl(int port, RequestHandler<SimpleRequest, SimpleResponse> processor) {
		super(port, SimpleJRestProtocolImpl.SERIALIZER, SimpleJRestProtocolImpl.init(processor));
	}

	/**
	 * Creates simple waiter protocol with given port and various processors.
	 * 
	 * @param port
	 * @param processors
	 */
	@SafeVarargs
	public SimpleWaiterProtocolImpl(int port, RequestHandler<SimpleRequest, SimpleResponse>... processors) {
		super(port, SimpleJRestProtocolImpl.SERIALIZER, SimpleJRestProtocolImpl.init(processors));
	}

}
