package cz.martlin.jrest.examples.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;
import cz.martlin.jrest.waiter.JRestWaiterShift;

public class CommonsServiceApp {

	private static final Logger LOG = LoggerFactory.getLogger(CommonsServiceApp.class);

	private static final int PORT = 2016;

	public static JarmilProtocol PROTOCOL = createProtocol();

	public static void main(String[] args) {
		LOG.info("Starting");

		JRestWaiterShift<JarmilRequest, JarmilResponse> shift = new JRestWaiterShift<>(PROTOCOL);

		shift.startWaiter();
		LOG.info("Started");
	}

	private static JarmilProtocol createProtocol() {
		JarmilEnvironment environment = new JarmilEnvironment();

		CommonsService service = new CommonsService();
		environment.addObject(CommonsService.NAME, service);

		JarmilProtocol protocol = new JarmilProtocol(PORT, environment);
		return protocol;
	}

}
