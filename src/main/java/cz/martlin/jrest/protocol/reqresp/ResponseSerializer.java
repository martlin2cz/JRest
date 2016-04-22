package cz.martlin.jrest.protocol.reqresp;

import cz.martlin.jrest.impl.simple.reqresps.SimpleResponse;
import cz.martlin.jrest.misc.JRestException;

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
