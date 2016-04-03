package cz.martlin.jrest.protocol;

public interface GuestProtocol {
	public int getPort();
	public String getWaiterHost();
	
	public RequestSerializer getRequestSerializer();
	public ResponseSerializer getReponseDeserializer();
}
