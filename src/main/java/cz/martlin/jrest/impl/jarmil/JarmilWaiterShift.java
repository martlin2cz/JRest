package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.waiter.JRestWaiterShift;

public class JarmilWaiterShift extends JRestWaiterShift<JarmilRequest, JarmilResponse> {

	public JarmilWaiterShift(JarmilProtocol protocol) {
		super(protocol);
	}

	public JarmilWaiterShift(int port, JarmilEnvironment environment) {
		super(new JarmilProtocol(port, environment));
	}

}
