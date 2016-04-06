package cz.martlin.jrest.protocol.reqresp;

import cz.martlin.jrest.misc.JRestException;

/**
 * Serializer and deserializer of {@link JRestRequest} instances.
 * 
 * @author martin
 *
 */
public interface RequestSerializer {

	/**
	 * Serializes given request.
	 * 
	 * @param request
	 * @return
	 * @throws JRestException
	 */
	public String serializeRequest(JRestRequest request) throws JRestException;

	/**
	 * Deserializes given request.
	 * 
	 * @param serialized
	 * @return
	 * @throws JRestException
	 */
	public JRestRequest deserializeRequest(String serialized) throws JRestException;

}
