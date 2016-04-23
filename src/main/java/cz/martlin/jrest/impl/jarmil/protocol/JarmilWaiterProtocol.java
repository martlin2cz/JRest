package cz.martlin.jrest.impl.jarmil.protocol;

import cz.martlin.jrest.impl.dflt.protocol.DefaultWaiterProtocolImpl;
import cz.martlin.jrest.impl.jarmil.misc.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;

public class JarmilWaiterProtocol extends DefaultWaiterProtocolImpl<JarmilRequest, JarmilResponse> {

	public JarmilWaiterProtocol(int port, JarmilEnvironment environment) {
		super(port, JarmilProtocol.SERIALIZER, JarmilProtocol.initHandler(environment));
	}

	public static JarmilWaiterProtocol createSingle(int port, TargetOnWaiter target) {
		JarmilEnvironment environment = new JarmilEnvironment(target);
		return new JarmilWaiterProtocol(port, environment);
	}

}
