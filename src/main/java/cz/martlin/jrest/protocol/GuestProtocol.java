package cz.martlin.jrest.protocol;

import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;

/**
 * Protocol on the guest side.
 * 
 * @author martin
 *
 */
public interface GuestProtocol {
	/**
	 * Returns the port of the communication.
	 * 
	 * @return
	 */
	public int getPort();

	/**
	 * Returns the host of the waiter (probably "localhost").
	 * 
	 * @return
	 */
	public String getWaiterHost();

	/**
	 * Returns serializer of requests.
	 * 
	 * @return
	 */
	public RequestSerializer getRequestSerializer();

	/**
	 * Returns deserializer of responses.
	 * 
	 * @return
	 */
	public ResponseSerializer getReponseDeserializer();
}
