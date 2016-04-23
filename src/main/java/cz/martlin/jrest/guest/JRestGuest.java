package cz.martlin.jrest.guest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;

/**
 * Guest in JRest restaurant, who sends commands to its waiter.
 * 
 * @author martin
 *
 */
public class JRestGuest<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final GuestProtocol<RQT, RST> protocol;
	private final JRestClient client;

	/**
	 * Creates guest communicating with given protocol.
	 * 
	 * @param protocol
	 */
	public JRestGuest(GuestProtocol<RQT, RST> protocol) {
		this.protocol = protocol;
		this.client = new JRestClient(protocol.getPort(), protocol.getWaiterHost());

		log.info("Guest ready");
	}

	public GuestProtocol<RQT, RST> getProtocol() {
		return protocol;
	}
	
	/**
	 * Sends given request to waiter and awaits the response.
	 * 
	 * @param request
	 * @return
	 * @throws JRestException
	 */
	public RST sendRequest(RQT request) throws JRestException {
		log.debug("Sending request: {}", request);

		String req = protocol.getRequestSerializer().serializeRequest(request);
		log.trace("Serialized request: {}", req);

		String resp = client.send(req);
		log.trace("Serialized response: {}", resp);

		RST response = protocol.getReponseDeserializer().deserializeResponse(resp);

		log.debug("Got response: {}", response);
		return response;

	}

}
