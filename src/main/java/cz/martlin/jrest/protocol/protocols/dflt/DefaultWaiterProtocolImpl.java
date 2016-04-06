package cz.martlin.jrest.protocol.protocols.dflt;

import java.util.List;

import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.BaseProtocolImpl;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.protocol.serializers.ReqRespSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * The default implementation of {@link WaiterProtocol}.
 * 
 * @author martin
 *
 */
public class DefaultWaiterProtocolImpl extends BaseProtocolImpl implements WaiterProtocol {

	private final List<RequestHandler> processors;

	public DefaultWaiterProtocolImpl(int port, RequestSerializer requestSerializer,
			ResponseSerializer responseSerializer, List<RequestHandler> handlers) {

		super(port, requestSerializer, responseSerializer);
		this.processors = handlers;
	}

	public DefaultWaiterProtocolImpl(int port, ReqRespSerializer serializer, List<RequestHandler> handlers) {
		super(port, serializer, serializer);
		this.processors = handlers;
	}

	@Override
	public RequestSerializer getRequestDeserializer() {
		return requestSerializer;
	}

	@Override
	public ResponseSerializer getReponseSerializer() {
		return responseSerializer;
	}

	@Override
	public List<RequestHandler> getHandlers() {
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
		DefaultWaiterProtocolImpl other = (DefaultWaiterProtocolImpl) obj;
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
