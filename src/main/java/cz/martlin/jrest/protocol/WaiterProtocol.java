package cz.martlin.jrest.protocol;

import java.util.List;

import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Represents protocol of communication of waiter side.
 * 
 * @author martin
 *
 */
public interface WaiterProtocol {

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
	public RequestSerializer getRequestDeserializer();

	/**
	 * Returns serializer of responses.
	 * 
	 * @return
	 */
	public ResponseSerializer getReponseSerializer();

	/**
	 * Returns list of request handlers.
	 * 
	 * @return
	 */
	public List<RequestHandler> getHandlers();
}
