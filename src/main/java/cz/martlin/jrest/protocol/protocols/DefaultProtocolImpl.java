package cz.martlin.jrest.protocol.protocols;

import cz.martlin.jrest.protocol.GuestProtocol;
import cz.martlin.jrest.protocol.RequestSerializer;
import cz.martlin.jrest.protocol.ResponseSerializer;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.serializers.ReqRespSerializer;
import cz.martlin.jrest.waiter.CommandProcessor;

public class DefaultProtocolImpl implements GuestProtocol, WaiterProtocol {

	private final int port;
	private final String host;

	private final ReqRespSerializer serializer;

	private final CommandProcessor processor;

	public DefaultProtocolImpl(int port, String host, ReqRespSerializer serializer, CommandProcessor processor) {
		super();
		this.port = port;
		this.host = host;
		this.serializer = serializer;
		this.processor = processor;
	}

	@Override
	public int getPort() {
		return port;
	}

	@Override
	public String getWaiterHost() {
		return host;
	}

	@Override
	public RequestSerializer getRequestDeserializer() {
		return serializer;
	}

	@Override
	public ResponseSerializer getReponseSerializer() {
		return serializer;
	}

	@Override
	public RequestSerializer getRequestSerializer() {
		return serializer;
	}

	@Override
	public ResponseSerializer getReponseDeserializer() {
		return serializer;
	}

	@Override
	public CommandProcessor getProcessor() {
		return processor;
	}

}
