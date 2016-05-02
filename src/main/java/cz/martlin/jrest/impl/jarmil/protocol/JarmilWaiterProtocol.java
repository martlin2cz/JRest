package cz.martlin.jrest.impl.jarmil.protocol;

import cz.martlin.jrest.impl.dflt.protocol.DefaultWaiterProtocolImpl;
import cz.martlin.jrest.impl.jarmil.handler.Echoer;
import cz.martlin.jrest.impl.jarmil.handler.WaiterStopper;
import cz.martlin.jrest.impl.jarmil.misc.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;

/**
 * The Waiter Jarmil protocol.
 * 
 * @author martin
 *
 */
public class JarmilWaiterProtocol extends DefaultWaiterProtocolImpl<JarmilRequest, JarmilResponse> {

	public JarmilWaiterProtocol(int port, JarmilEnvironment environment) {
		super(port, JarmilProtocol.SERIALIZER, JarmilProtocol.initHandler(environment));
	}

	/**
	 * Creates "single" protocol (the protocol with the only one (in fact some
	 * also with {@link Echoer} and {@link WaiterStopper}) target.
	 * 
	 * @param port
	 * @param target
	 * @return
	 */
	public static JarmilWaiterProtocol createSingle(int port, TargetOnWaiter target) {
		JarmilEnvironment environment = new JarmilEnvironment(target);
		return new JarmilWaiterProtocol(port, environment);
	}

	/**
	 * Creates protocol of given port and targets.
	 * 
	 * @param port
	 * @param targets
	 * @return
	 */
	public static JarmilWaiterProtocol create(int port, TargetOnWaiter... targets) {
		JarmilEnvironment environment = new JarmilEnvironment(targets);
		return new JarmilWaiterProtocol(port, environment);
	}

}
