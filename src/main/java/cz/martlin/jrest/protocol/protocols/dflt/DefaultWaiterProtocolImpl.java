package cz.martlin.jrest.protocol.protocols.dflt;

import java.util.List;

import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.BaseProtocolImpl;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.protocol.serializer.ReqRespSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * The default implementation of {@link WaiterProtocol}.
 * 
 * @author martin
 *
 */
public class DefaultWaiterProtocolImpl<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> //
		extends BaseProtocolImpl<RQT, RST> implements WaiterProtocol<RQT, RST> {

	private final List<RequestHandler<RQT, RST>> processors;

	public DefaultWaiterProtocolImpl(int port, RequestSerializer<RQT> requestSerializer,
			ResponseSerializer<RST> responseSerializer, List<RequestHandler<RQT, RST>> handlers) {

		super(port, requestSerializer, responseSerializer);
		this.processors = handlers;
	}

	public DefaultWaiterProtocolImpl(int port, ReqRespSerializer<RQT, RST> serializer,
			List<RequestHandler<RQT, RST>> handlers) {
		super(port, serializer, serializer);
		this.processors = handlers;
	}

	@Override
	public RequestSerializer<RQT> getRequestDeserializer() {
		return requestSerializer;
	}

	@Override
	public ResponseSerializer<RST> getReponseSerializer() {
		return responseSerializer;
	}

	@Override
	public List<RequestHandler<RQT, RST>> getHandlers() {
		return processors;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((processors == null) ? 0 : processors.hashCode());
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
		DefaultWaiterProtocolImpl<?, ?> other = (DefaultWaiterProtocolImpl<?, ?>) obj;
		if (processors == null) {
			if (other.processors != null)
				return false;
		} else if (!processors.equals(other.processors))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultWaiterProtocolImpl [processors=" + processors + ", port=" + port + ", requestSerializer="
				+ requestSerializer + ", responseSerializer=" + responseSerializer + "]";
	}

}
