package cz.martlin.jrest.impl.jarmil.protocol;

import cz.martlin.jrest.impl.dflt.protocol.DefaultGuestProtocolImpl;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;

/**
 * The guest protocol of Jarmil.
 * 
 * @author martin
 *
 */
public class JarmilGuestProtocol extends DefaultGuestProtocolImpl<JarmilRequest, JarmilResponse> {

	public JarmilGuestProtocol(int port) {
		super(port, DFLT_HOST, JarmilProtocol.SERIALIZER);
	}

	public JarmilGuestProtocol(int port, String host) {
		super(port, host, JarmilProtocol.SERIALIZER);
	}

	/**
	 * Creates guest protocol of given port.
	 * 
	 * @param port
	 * @return
	 */
	public static JarmilGuestProtocol create(int port) {
		return new JarmilGuestProtocol(port);
	}

	/**
	 * Creates guest protocol of given port and host.
	 * 
	 * @param port
	 * @param host
	 * @return
	 */
	public static JarmilGuestProtocol create(int port, String host) {
		return new JarmilGuestProtocol(port, host);
	}

}
