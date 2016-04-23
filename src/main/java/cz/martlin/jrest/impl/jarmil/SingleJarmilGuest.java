package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.handlers.JarmilResponseStatus;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.misc.JRestException;

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
