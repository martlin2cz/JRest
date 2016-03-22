package cz.martlin.jrest.guest;

import cz.martlin.jrest.misc.CommunicationProtocol;
import cz.martlin.jrest.misc.JRestException;

public class JRestGuest {
	//private final Logger log = LoggerFactory.getLogger(getClass());

	private final CommunicationProtocol protocol;
	private final JRestClient client;

	public JRestGuest(CommunicationProtocol protocol) {
		this.protocol = protocol;
		this.client = new JRestClient(protocol.getPort(), protocol.getServerHost());
	}

	public String sendCommand(String command) throws JRestException {
		return client.send(command);
	}

	public void stopWaiter() throws JRestException {
		sendCommand(protocol.getExitCommand());
	}

}
