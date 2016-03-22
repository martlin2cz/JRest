package cz.martlin.jrest.waiter;

import cz.martlin.jrest.misc.Interruptable;

public class DaemonThread extends Thread implements Interruptable {

	public DaemonThread(Runnable body) {
		super(body, "Daemon");
		setDaemon(true);
	}

}
