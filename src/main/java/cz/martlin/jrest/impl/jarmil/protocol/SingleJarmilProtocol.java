package cz.martlin.jrest.impl.jarmil.protocol;

/**
 * Jarmil protocol to be used in single jarmil implementation.
 * 
 * @author martin
 *
 */
public class SingleJarmilProtocol extends JarmilProtocol {

	public SingleJarmilProtocol(int port, Class<?> clazz) {
		super(port, new SingleJarmilEnvironment(clazz));
	}

	public SingleJarmilProtocol(int port, String name, Object object) {
		super(port, new SingleJarmilEnvironment(name, object));
	}

	public SingleJarmilProtocol(int port, String host, Class<?> clazz) {
		super(port, host, new SingleJarmilEnvironment(clazz));
	}

	public SingleJarmilProtocol(int port, String host, String name, Object object) {
		super(port, host, new SingleJarmilEnvironment(name, object));
	}

	public SingleJarmilEnvironment getEnvironment() {
		return (SingleJarmilEnvironment) super.getEnvironment();
	}
}
