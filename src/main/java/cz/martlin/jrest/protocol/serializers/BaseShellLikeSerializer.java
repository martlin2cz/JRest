package cz.martlin.jrest.protocol.serializers;

import java.util.List;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.misc.DelimitedStringsSerializer;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;
import cz.martlin.jrest.protocol.serializer.ReqRespSerializer;

/**
 * TODO doc
 * 
 * @author martin
 *
 * @param <RQT>
 * @param <RST>
 */
public abstract class BaseShellLikeSerializer<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse>
		implements ReqRespSerializer<RQT, RST> {

	private final DelimitedStringsSerializer serializer = new DelimitedStringsSerializer();

	public BaseShellLikeSerializer() {
		super();
	}

	protected abstract RST listToResponse(List<String> list) throws Exception;

	protected abstract List<String> responseToList(RST response) throws Exception;

	protected abstract RQT listToRequest(List<String> list) throws Exception;

	protected abstract List<String> requestToList(RQT request) throws Exception;

	@Override
	public String serializeRequest(RQT request) throws JRestException {
		try {
			List<String> list = requestToList(request);
			return serializer.serialize(list);
		} catch (Exception e) {
			throw new JRestException("Cannot seriliaze request: " + request, e);
		}
	}

	@Override
	public RQT deserializeRequest(String serialized) throws JRestException {
		try {
			List<String> list = serializer.deserialize(serialized);
			return listToRequest(list);
		} catch (Exception e) {
			throw new JRestException("Cannot deseriliaze request: " + serialized, e);
		}
	}

	@Override
	public String serializeResponse(RST response) throws JRestException {
		try {
			List<String> list = responseToList(response);
			return serializer.serialize(list);
		} catch (Exception e) {
			throw new JRestException("Cannot seriliaze response: " + response, e);
		}
	}

	@Override
	public RST deserializeResponse(String serialized) throws JRestException {
		try {
			List<String> list = serializer.deserialize(serialized);
			return listToResponse(list);
		} catch (Exception e) {
			throw new JRestException("Cannot deseriliaze response: " + serialized, e);
		}
	}

}