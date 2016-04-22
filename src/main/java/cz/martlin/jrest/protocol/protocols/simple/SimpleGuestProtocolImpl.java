package cz.martlin.jrest.protocol.protocols.simple;

import cz.martlin.jrest.protocol.protocols.dflt.DefaultGuestProtocolImpl;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;

/**
 * The simple guest procotol. Uses {@link SimpleJRestProtocolImpl}'s serializer.
 * 
 * @author martin
 *
 */
public class SimpleGuestProtocolImpl extends DefaultGuestProtocolImpl<SimpleRequest, SimpleResponse> {

	/**
	 * Creates simple guest protocol with given port and default host (
	 * {@link SimpleJRestProtocolImpl#DFLT_HOST}).
	 * 
	 * @param port
	 */
	public SimpleGuestProtocolImpl(int port) {
		super(port, SimpleJRestProtocolImpl.DFLT_HOST, SimpleJRestProtocolImpl.SERIALIZER);
	}

	/**
	 * Creates simple guest protocol with given port and given host.
	 * 
	 * @param port
	 * @param host
	 */
	public SimpleGuestProtocolImpl(int port, String host) {
		super(port, host, SimpleJRestProtocolImpl.SERIALIZER);
	}

}
