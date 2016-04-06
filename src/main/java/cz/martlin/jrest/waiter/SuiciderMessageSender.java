package cz.martlin.jrest.waiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.guest.JRestClient;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.handlers.StopWaiterCommandHandler;

/**
 * When the waiter's server is interrupted directly (not by interrupting
 * request, like in {@link StopWaiterCommandHandler}) the server has to be
 * informed about that. So, the awaing of some request must be interrupted.
 * 
 * This class sends to the (interrupted) server (directly, with no waiter/guest)
 * suicide message (with text {@link #SUICIDE_MESSAGE}). This message awakes
 * server and makes them to stop.
 * 
 * @author martin
 *
 */
public class SuiciderMessageSender {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private static final String SUICIDE_HOST = "localhost";
	private static final String SUICIDE_MESSAGE = "Hasta la vista, baby!";

	private final WaiterProtocol protocol;

	public SuiciderMessageSender(WaiterProtocol protocol) {
		this.protocol = protocol;
	}

	/**
	 * Sends the (last, to the interrupted server, else is the behaviour
	 * unknown) suicide message to server. The message contains simple
	 * {@value #SUICIDE_MESSAGE}, so server should exit immediatelly after the
	 * recieve of the message instead of try to decode and handle it.
	 * 
	 * @return
	 */
	public boolean sendSuicideMessage() {
		log.debug("Sending suicide message");
		JRestClient cli = new JRestClient(protocol.getPort(), SUICIDE_HOST);

		try {
			cli.send(SUICIDE_MESSAGE);
			log.info("Suicide message succesfully sent");
			return true;
		} catch (JRestException e) {
			log.error("Suicide message send failed", e);
			return false;
		}
	}
}
