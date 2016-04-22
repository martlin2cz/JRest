package cz.martlin.jrest.protocol.protocols.simple;

import cz.martlin.jrest.protocol.protocols.dflt.DefaultWaiterProtocolImpl;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
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
