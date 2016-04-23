package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.SingleJarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.protocol.SingleJarmilProtocol;
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

	public SingleJarmilGuest(SingleJarmilProtocol protocol) {
		super(protocol);
	}

	@Override
	public SingleJarmilProtocol getProtocol() {
		return (SingleJarmilProtocol) super.getProtocol();
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
		JarmilRequest request = createRequest(method, arguments);

		JarmilResponse response = sendRequest(request);

		return handleResponse(response);
	}

	private JarmilRequest createRequest(String method, Object... arguments) throws JRestException {
		SingleJarmilEnvironment env = getProtocol().getEnvironment();

		JarmilRequest request;
		if (env.getClazz() != null) {
			request = JarmilRequest.create(env.getClazz(), env.getName(), method, arguments);
		} else {
			request = JarmilRequest.create(env.getClazz(), method, arguments);
		}
		return request;
	}

	private <T> T handleResponse(JarmilResponse response) throws JRestException {
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
