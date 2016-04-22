package cz.martlin.jrest.protocol.reqresp;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;

/**
 * Serializer and deserializer of {@link SimpleResponse} instances.
 * 
 * @author martin
 *
 */
public interface ResponseSerializer<T extends JRestAbstractResponse> {

	/**
	 * Serializes given response.
	 * 
	 * @param response
	 * @return
	 * @throws JRestException
	 */
	public String serializeResponse(T response) throws JRestException;

	/**
	 * Deserializes given response.
	 * 
	 * @param serialized
	 * @return
	 * @throws JRestException
	 */
	public T deserializeResponse(String serialized) throws JRestException;

}
