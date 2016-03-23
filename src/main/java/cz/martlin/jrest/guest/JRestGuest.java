package cz.martlin.jrest.guest;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.JRestException;

/**
 * Guest in JRest restaurant, who sends commands to its waiter. Optionally,
 * guest can waiter stop.
 * 
 * @author martin
 *
 */
public class JRestGuest {

	private final CommunicationProtocol protocol;
	private final JRestClient client;

	/**
	 * Creates guest communicating with given procotol.
	 * 
	 * @param protocol
	 */
	public JRestGuest(CommunicationProtocol protocol) {
		this.protocol = protocol;
		this.client = new JRestClient(protocol.getPort(), protocol.getServerHost());
	}

	/**
	 * Sends given "command" (whathever string data which can server handle).
	 * 
	 * @param command
	 * @return
	 * @throws JRestException
	 */
	public String sendCommand(String command) throws JRestException {
		return client.send(command);
	}

	/**
	 * Sends command to waiter stop.
	 * 
	 * @throws JRestException
	 */
	public void stopWaiter() throws JRestException {
		sendCommand(protocol.getExitCommand());
	}

}
