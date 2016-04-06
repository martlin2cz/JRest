package cz.martlin.jrest.protocol.reqresp;

import cz.martlin.jrest.misc.JRestException;

/**
 * Serializer and deserializer of {@link JRestResponse} instances.
 * 
 * @author martin
 *
 */
public interface ResponseSerializer {

	/**
	 * Serializes given response.
	 * 
	 * @param response
	 * @return
	 * @throws JRestException
	 */
	public String serializeResponse(JRestResponse response) throws JRestException;

	/**
	 * Deserializes given response.
	 * 
	 * @param serialized
	 * @return
	 * @throws JRestException
	 */
	public JRestResponse deserializeResponse(String serialized) throws JRestException;

}
