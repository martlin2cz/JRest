package cz.martlin.jrest.examples.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.impl.jarmil.JarmilWaiterShift;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilWaiterProtocol;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.targets.waiter.ObjectOnWaiterTarget;

public class CommonsServiceApp {

	private static final Logger LOG = LoggerFactory.getLogger(CommonsServiceApp.class);

	public static final int PORT = 2016;
	public static final String NAME = "commons";

	public static void main(String[] args) {
		LOG.info("Starting");

		final CommonsService service = new CommonsService();
		final TargetOnWaiter target = ObjectOnWaiterTarget.create(NAME, service);
		final JarmilWaiterProtocol protocol = JarmilWaiterProtocol.createSingle(PORT, target);

		JarmilWaiterShift shift = new JarmilWaiterShift(protocol);

		shift.startWaiter();
		LOG.info("Started");
	}

}
