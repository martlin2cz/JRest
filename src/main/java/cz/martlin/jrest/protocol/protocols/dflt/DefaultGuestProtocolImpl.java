package cz.martlin.jrest.protocol.protocols.dflt;

import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.protocols.BaseProtocolImpl;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.protocol.serializers.ReqRespSerializer;

/**
 * The default implementation of {@link GuestProtocol}.
 * 
 * @author martin
 *
 */
public class DefaultGuestProtocolImpl extends BaseProtocolImpl implements GuestProtocol {

	private final String host;

	public DefaultGuestProtocolImpl(int port, String host, RequestSerializer requestSerializer,
			ResponseSerializer responseSerializer) {

		super(port, requestSerializer, responseSerializer);
		this.host = host;
	}

	public DefaultGuestProtocolImpl(int port, String host, ReqRespSerializer serializer) {
		super(port, serializer, serializer);
		this.host = host;
	}

	@Override
	public String getWaiterHost() {
		return host;
	}

	@Override
	public RequestSerializer getRequestSerializer() {
		return requestSerializer;
	}

	@Override
	public ResponseSerializer getReponseDeserializer() {
		return responseSerializer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
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
		DefaultGuestProtocolImpl other = (DefaultGuestProtocolImpl) obj;
		if (host == null) {
			if (other.host != null)
				return false;
		} else if (!host.equals(other.host))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DefaultGuestProtocolImpl [host=" + host + ", port=" + port + ", requestSerializer=" + requestSerializer
				+ ", responseSerializer=" + responseSerializer + "]";
	}

}
