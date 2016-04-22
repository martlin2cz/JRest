package cz.martlin.jrest.protocol;

import java.util.List;

import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Represents protocol of communication of waiter side.
 * 
 * @author martin
 *
 */
public interface WaiterProtocol<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> {

	/**
	 * Returns port of the communication.
	 * 
	 * @return
	 */
	public int getPort();

	/**
	 * Returns deserializer of requests.
	 * 
	 * @return
	 */
	public RequestSerializer<RQT> getRequestDeserializer();

	/**
	 * Returns serializer of responses.
	 * 
	 * @return
	 */
	public ResponseSerializer<RST> getReponseSerializer();

	/**
	 * Returns list of request handlers.
	 * 
	 * @return
	 */
	public List<RequestHandler<RQT, RST>> getHandlers();
}
