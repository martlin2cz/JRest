package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.SingleJarmilProtocol;

/**
 * Implements Waiter shift such that handles only Jarmil requests and the only
 * request of one given class/named object.
 * 
 * @author martin
 *
 */
public class SingleJarmilWaiterShift extends JarmilWaiterShift {

	public SingleJarmilWaiterShift(SingleJarmilProtocol protocol) {
		super(protocol);
	}
}
