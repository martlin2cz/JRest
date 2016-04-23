package cz.martlin.jrest.impl.jarmil.protocol;

import cz.martlin.jrest.impl.dflt.protocol.DefaultGuestProtocolImpl;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;

public class JarmilGuestProtocol extends DefaultGuestProtocolImpl<JarmilRequest, JarmilResponse> {

	public JarmilGuestProtocol(int port) {
		super(port, DFLT_HOST, JarmilProtocol.SERIALIZER);
	}
	
	public JarmilGuestProtocol(int port, String host) {
		super(port, host, JarmilProtocol.SERIALIZER);
	}

}
