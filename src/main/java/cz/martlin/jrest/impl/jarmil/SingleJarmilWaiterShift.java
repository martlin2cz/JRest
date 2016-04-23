package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;

/**
 * Implements Waiter shift such that handles only Jarmil requests and the only
 * request of one given class/named object.
 * 
 * @author martin
 *
 */
public class SingleJarmilWaiterShift extends JarmilWaiterShift {

	public SingleJarmilWaiterShift(JarmilProtocol protocol) {
		super(protocol);
	}

	public SingleJarmilWaiterShift(int port, Class<?> clazz) {
		super(createProtocol(port, createEnvironment(clazz)));
	}

	public SingleJarmilWaiterShift(int port, String name, Object object) {
		super(createProtocol(port, createEnvironment(name, object)));
	}

	public static JarmilEnvironment createEnvironment(Class<?> clazz) {
		JarmilEnvironment environment = new JarmilEnvironment();
		environment.addClass(clazz);
		return environment;
	}

	public static JarmilEnvironment createEnvironment(String name, Object object) {
		JarmilEnvironment environment = new JarmilEnvironment();
		environment.addObject(name, object);
		return environment;
	}

	public static JarmilProtocol createProtocol(int port, JarmilEnvironment environment) {
		return new JarmilProtocol(port, environment);
	}

	public static JarmilProtocol createProtocol(int port, String host, JarmilEnvironment environment) {
		return new JarmilProtocol(port, host, environment);
	}

}
