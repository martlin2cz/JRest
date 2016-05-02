package cz.martlin.jrest.examples.commons;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

<<<<<<< HEAD
import cz.martlin.jrest.impl.jarmil.JarmilWaiterShift;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilWaiterProtocol;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.targets.waiter.ObjectOnWaiterTarget;
=======
import cz.martlin.jrest.impl.jarmil.SingleJarmilWaiterShift;
import cz.martlin.jrest.impl.jarmil.protocol.SingleJarmilProtocol;
>>>>>>> branch 'master' of https://github.com/martlin2cz/JRest.git

public class CommonsServiceApp {

	private static final Logger LOG = LoggerFactory.getLogger(CommonsServiceApp.class);

	public static final int PORT = 2016;
	public static final String NAME = "commons";
<<<<<<< HEAD
=======
	public static final CommonsService SERVICE = new CommonsService();
	public static final SingleJarmilProtocol PROTOCOL = new SingleJarmilProtocol(PORT, NAME, SERVICE);
>>>>>>> branch 'master' of https://github.com/martlin2cz/JRest.git

	public static void main(String[] args) {
		LOG.info("Starting");

<<<<<<< HEAD
		final CommonsService service = new CommonsService();
		final TargetOnWaiter target = ObjectOnWaiterTarget.create(NAME, service);
		final JarmilWaiterProtocol protocol = JarmilWaiterProtocol.createSingle(PORT, target);

		JarmilWaiterShift shift = new JarmilWaiterShift(protocol);
=======
		SingleJarmilWaiterShift shift = new SingleJarmilWaiterShift(PROTOCOL);
>>>>>>> branch 'master' of https://github.com/martlin2cz/JRest.git

		shift.startWaiter();
		LOG.info("Started");
	}

}
