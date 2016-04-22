package cz.martlin.jrest.protocol.reqresp;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;

/**
 * Serializer and deserializer of {@link SimpleRequest} instances.
 * 
 * @author martin
 *
 */
public interface RequestSerializer<T extends JRestAbstractRequest> {

	/**
	 * Serializes given request.
	 * 
	 * @param request
	 * @return
	 * @throws JRestException
	 */
	public String serializeRequest(T request) throws JRestException;

	/**
	 * Deserializes given request.
	 * 
	 * @param serialized
	 * @return
	 * @throws JRestException
	 */
	public T deserializeRequest(String serialized) throws JRestException;

}
