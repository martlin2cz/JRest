package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilGuestProtocol;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;

/**
 * Implements custom, Jarmil specific guest.
 * 
 * @author martin
 *
 */
public class JarmilGuest extends JRestGuest<JarmilRequest, JarmilResponse> {

	public JarmilGuest(JarmilProtocol protocol) {
		super(protocol);
	}

	public JarmilGuest(JarmilGuestProtocol protocol) {
		super(protocol);
	}

}
