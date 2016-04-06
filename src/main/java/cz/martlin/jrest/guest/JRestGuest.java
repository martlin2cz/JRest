package cz.martlin.jrest.guest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;

/**
 * Guest in JRest restaurant, who sends commands to its waiter.
 * 
 * @author martin
 *
 */
public class JRestGuest {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final GuestProtocol protocol;
	private final JRestClient client;

	/**
	 * Creates guest communicating with given protocol.
	 * 
	 * @param protocol
	 */
	public JRestGuest(GuestProtocol protocol) {
		this.protocol = protocol;
		this.client = new JRestClient(protocol.getPort(), protocol.getWaiterHost());

		log.info("Guest ready");
	}

	/**
	 * Sends given request to waiter and awaits the response.
	 * 
	 * @param request
	 * @return
	 * @throws JRestException
	 */
	public JRestResponse sendRequest(JRestRequest request) throws JRestException {
		log.debug("Sending request: {}", request);

		String req = protocol.getRequestSerializer().serializeRequest(request);
		log.trace("Serialized request: {}", req);

		String resp = client.send(req);
		log.trace("Serialized response: {}", resp);

		JRestResponse response = protocol.getReponseDeserializer().deserializeResponse(resp);

		log.debug("Got response: {}", response);
		return response;

	}

}
