package cz.martlin.jrest.impl.dflt.protocol;

import java.util.List;

import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.BaseProtocolImpl;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.protocol.serializer.ReqRespSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Default implementation of (both guest and waiter) protocol.
 * 
 * @author martin
 *
 */
public class DefaultJRestProtocolImpl<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> //
		extends BaseProtocolImpl<RQT, RST> //
		implements GuestProtocol<RQT, RST>, WaiterProtocol<RQT, RST> {

	
	private final String host;
	private final List<RequestHandler<RQT, RST>> handlers;

	public DefaultJRestProtocolImpl(int port, String host, //
			RequestSerializer<RQT> requestSerializer, ResponseSerializer<RST> responseSerializer, //
			List<RequestHandler<RQT, RST>> handlers) {
		super(port, requestSerializer, responseSerializer);
		this.host = host;
		this.handlers = handlers;
	}

	public DefaultJRestProtocolImpl(int port, String host, //
			ReqRespSerializer<RQT, RST> serializer, //
			List<RequestHandler<RQT, RST>> handlers) {
		super(port, serializer, serializer);
		this.host = host;
		this.handlers = handlers;
	}

	@Override
	public String getWaiterHost() {
		return host;
	}

	@Override
	public List<RequestHandler<RQT, RST>> getHandlers() {
		return handlers;
	}

	@Override
	public RequestSerializer<RQT> getRequestSerializer() {
		return getTheRequestSerializer();
	}

	@Override
	public RequestSerializer<RQT> getRequestDeserializer() {
		return getTheRequestSerializer();
	}

	@Override
	public ResponseSerializer<RST> getReponseSerializer() {
		return getTheResponseSerializer();
	}

	@Override
	public ResponseSerializer<RST> getReponseDeserializer() {
		return getTheResponseSerializer();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((handlers == null) ? 0 : handlers.hashCode());
		result = prime * result + ((host == null) ? 0 : host.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultJRestProtocolImpl<?, ?> other = (DefaultJRestProtocolImpl<?, ?>) obj;
		if (handlers == null) {
			if (other.handlers != null)
				return false;
		} else if (!handlers.equals(other.handlers))
			return false;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultJRestProtocolImpl [host=" + host + ", handlers=" + handlers + ", port=" + port
				+ ", requestSerializer=" + requestSerializer + ", responseSerializer=" + responseSerializer + "]";
	}

}
