package cz.martlin.jrest.guest;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.JRestResponse;

/**
 * Guest in JRest restaurant, who sends commands to its waiter.
 * 
 * @author martin
 *
 */
public class JRestGuest {

	private final GuestProtocol protocol;
	private final JRestClient client;

	/**
	 * Creates guest communicating with given procotol.
	 * 
	 * @param protocol
	 */
	public JRestGuest(GuestProtocol protocol) {
		this.protocol = protocol;
		this.client = new JRestClient(protocol.getPort(), protocol.getWaiterHost());
	}

	public JRestResponse sendCommand(JRestRequest command) throws JRestException {
		String req = protocol.getRequestSerializer().serializeRequest(command);

		String resp = client.send(req);

		return protocol.getReponseDeserializer().deserializeResponse(resp);
	}

}
