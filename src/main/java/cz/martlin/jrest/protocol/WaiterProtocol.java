package cz.martlin.jrest.protocol;

import java.util.List;

import cz.martlin.jrest.waiter.RequestHandler;

public interface WaiterProtocol {
	public int getPort();

	public RequestSerializer getRequestDeserializer();

	public ResponseSerializer getReponseSerializer();
	
	public List<RequestHandler> getHandlers();
}
