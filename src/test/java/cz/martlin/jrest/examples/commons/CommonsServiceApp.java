package cz.martlin.jrest.examples.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.SingleJarmilWaiterShift;

public class CommonsServiceApp {

	private static final Logger LOG = LoggerFactory.getLogger(CommonsServiceApp.class);

	public static final int PORT = 2016;
	public static final String NAME = "commons";
	public static final CommonsService SERVICE = new CommonsService();

	public static void main(String[] args) {
		LOG.info("Starting");

		SingleJarmilWaiterShift shift = new SingleJarmilWaiterShift(PORT, NAME, SERVICE);

		shift.startWaiter();
		LOG.info("Started");
	}

}
