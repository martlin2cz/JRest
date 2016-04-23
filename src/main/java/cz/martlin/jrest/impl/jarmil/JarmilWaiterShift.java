package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilWaiterProtocol;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.waiter.JRestWaiterShift;

/**
 * Implements custom, Jarmil specific, Waiter shift
 * 
 * @author martin
 *
 */
public class JarmilWaiterShift extends JRestWaiterShift<JarmilRequest, JarmilResponse> {

	public JarmilWaiterShift(JarmilProtocol protocol) {
		super(protocol);
	}

	public JarmilWaiterShift(JarmilWaiterProtocol protocol) {
		super(protocol);
	}
}
