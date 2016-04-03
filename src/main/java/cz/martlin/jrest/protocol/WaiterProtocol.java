package cz.martlin.jrest.protocol;

import cz.martlin.jrest.waiter.CommandProcessor;

public interface WaiterProtocol {
	public int getPort();

	public RequestSerializer getRequestDeserializer();

	public ResponseSerializer getReponseSerializer();
	
	public CommandProcessor getProcessor();
}
