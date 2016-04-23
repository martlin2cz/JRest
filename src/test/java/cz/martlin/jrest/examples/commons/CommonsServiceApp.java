package cz.martlin.jrest.examples.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.SingleJarmilWaiterShift;
import cz.martlin.jrest.impl.jarmil.protocol.SingleJarmilProtocol;

public class CommonsServiceApp {

	private static final Logger LOG = LoggerFactory.getLogger(CommonsServiceApp.class);

	public static final int PORT = 2016;
	public static final String NAME = "commons";
	public static final CommonsService SERVICE = new CommonsService();
	public static final SingleJarmilProtocol PROTOCOL = new SingleJarmilProtocol(PORT, NAME, SERVICE);

	public static void main(String[] args) {
		LOG.info("Starting");

		SingleJarmilWaiterShift shift = new SingleJarmilWaiterShift(PROTOCOL);

		shift.startWaiter();
		LOG.info("Started");
	}

}
