package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponseStatus;
import cz.martlin.jrest.misc.JRestException;

/**
 * Implements "single-objected or single-classed" Jarmil guest. This class
 * provides method {@link #invoke(String, Object...)} to automatically call
 * method on class (static method) or object by given name on the waiter.
 * 
 * @author martin
 *
 */
public class SingleJarmilGuest extends JarmilGuest {

	private final Class<?> clazz;
	private final String name;

	public SingleJarmilGuest(int port, Class<?> clazz) {
		super(SingleJarmilWaiterShift.createProtocol(port, //
				SingleJarmilWaiterShift.createEnvironment(clazz)));

		this.clazz = clazz;
		this.name = null;
	}

	public SingleJarmilGuest(int port, String name, Object object) {
		super(SingleJarmilWaiterShift.createProtocol(port, //
				SingleJarmilWaiterShift.createEnvironment(name, object)));

		this.clazz = object.getClass();
		this.name = name;
	}

	public SingleJarmilGuest(int port, String host, Class<?> clazz) {
		super(SingleJarmilWaiterShift.createProtocol(port, host, //
				SingleJarmilWaiterShift.createEnvironment(clazz)));

		this.clazz = clazz;
		this.name = null;
	}

	public SingleJarmilGuest(int port, String host, String name, Object object) {
		super(SingleJarmilWaiterShift.createProtocol(port, host, //
				SingleJarmilWaiterShift.createEnvironment(name, object)));

		this.clazz = object.getClass();
		this.name = name;
	}

	/**
	 * Invokes given method on the waiter's object/class (specified in
	 * constructor). If the waiter fails, the exception is repacked and thrown.
	 * Yea, really!
	 * 
	 * @param method
	 * @param arguments
	 * @return
	 * @throws JRestException
	 */
	public <T> T invoke(String method, Object... arguments) throws JRestException {
		JarmilRequest request;
		if (name != null) {
			request = JarmilRequest.create(clazz, name, method, arguments);
		} else {
			request = JarmilRequest.create(clazz, method, arguments);
		}

		JarmilResponse response = sendRequest(request);

		if (response.getStatus() == JarmilResponseStatus.OK) {
			try {
				@SuppressWarnings("unchecked")
				T data = (T) response.getData();
				return data;
			} catch (ClassCastException e) {
				throw new JRestException("Cannot cast response", e);
			}
		} else {
			Throwable t = (Throwable) response.getData();
			throw new JRestException("Exception on waiter", t);

		}
	}

}
