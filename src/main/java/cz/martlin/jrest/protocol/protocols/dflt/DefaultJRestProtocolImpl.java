package cz.martlin.jrest.protocol.protocols.dflt;

import java.util.List;

import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.RequestSerializer;
import cz.martlin.jrest.protocol.ResponseSerializer;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.protocols.BaseProtocolImpl;
import cz.martlin.jrest.protocol.serializers.ReqRespSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

public class DefaultJRestProtocolImpl extends BaseProtocolImpl implements GuestProtocol, WaiterProtocol {

	private final String host;
	private final List<RequestHandler> handlers;

	public DefaultJRestProtocolImpl(int port, String host, RequestSerializer requestSerializer,
			ResponseSerializer responseSerializer, List<RequestHandler> handlers) {
		super(port, requestSerializer, responseSerializer);
		this.host = host;
		this.handlers = handlers;
	}

	public DefaultJRestProtocolImpl(int port, String host, ReqRespSerializer serializer,
			List<RequestHandler> handlers) {
		super(port, serializer, serializer);
		this.host = host;
		this.handlers = handlers;
	}

	@Override
	public String getWaiterHost() {
		return host;
	}

	@Override
	public List<RequestHandler> getHandlers() {
		return handlers;
	}

	@Override
	public RequestSerializer getRequestSerializer() {
		// TODO Auto-generated method stub
		return super.getTheRequestSerializer();
	}
	
	@Override
	public RequestSerializer getRequestDeserializer() {
		return getTheRequestSerializer();
	}

	@Override
	public ResponseSerializer getReponseSerializer() {
		return getTheResponseSerializer();
	}

	@Override
	public ResponseSerializer getReponseDeserializer() {
		return getTheResponseSerializer();
	}
	
	

}
