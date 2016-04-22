package cz.martlin.jrest.protocol.protocols;

import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;

/**
 * The base class for both guest and waiter protocols implementations.
 * 
 * @author martin
 *
 */
public class BaseProtocolImpl<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> {

	protected final int port;

	protected final RequestSerializer<RQT> requestSerializer;
	protected final ResponseSerializer<RST> responseSerializer;

	/**
	 * Creates instance of given params.
	 * 
	 * @param port
	 * @param requestSerializer
	 * @param responseSerializer
	 */
	public BaseProtocolImpl(int port, RequestSerializer<RQT> requestSerializer,
			ResponseSerializer<RST> responseSerializer) {
		super();
		this.port = port;
		this.requestSerializer = requestSerializer;
		this.responseSerializer = responseSerializer;
	}

	public int getPort() {
		return port;
	}

	public RequestSerializer<RQT> getTheRequestSerializer() {
		return requestSerializer;
	}

	public ResponseSerializer<RST> getTheResponseSerializer() {
		return responseSerializer;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + port;
		result = prime * result + ((requestSerializer == null) ? 0 : requestSerializer.hashCode());
		result = prime * result + ((responseSerializer == null) ? 0 : responseSerializer.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseProtocolImpl<?, ?> other = (BaseProtocolImpl<?, ?>) obj;
		if (port != other.port)
			return false;
		if (requestSerializer == null) {
			if (other.requestSerializer != null)
				return false;
		} else if (!requestSerializer.equals(other.requestSerializer))
			return false;
		if (responseSerializer == null) {
			if (other.responseSerializer != null)
				return false;
		} else if (!responseSerializer.equals(other.responseSerializer))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BaseProtocolImpl [port=" + port + ", requestSerializer=" + requestSerializer + ", responseSerializer="
				+ responseSerializer + "]";
	}

}
