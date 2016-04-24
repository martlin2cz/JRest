package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.JarmilGuestProtocol;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.misc.JRestException;

/**
 * Extends Jarmil guest to work with exactly one target. In this case allows to
 * invoke method directly only with their name and parameters.
 * 
 * @author martin
 *
 */
public class SingleJarmilGuest extends JarmilGuest {

	private final TargetOnGuest target;

	public SingleJarmilGuest(TargetOnGuest target, JarmilProtocol protocol) {
		super(protocol);
		this.target = target;
	}

	public SingleJarmilGuest(TargetOnGuest target, JarmilGuestProtocol protocol) {
		super(protocol);
		this.target = target;
	}

	/**
	 * Invokes given method with given parameters. Returns the recieved
	 * response. If error occurs, the exception is repacked and thrown.
	 * 
	 * @param method
	 * @param parameters
	 * @return
	 * @throws JRestException
	 */
	public <T> T invoke(String method, Object... parameters) throws JRestException {
		JarmilRequest request = JarmilRequest.create(target, method, parameters);

		JarmilResponse response = sendRequest(request);

		switch (response.getStatus()) {
		case OK: {
			@SuppressWarnings("unchecked")
			T data = (T) response.getData();
			return data;
		}
		case INVOCATION_FAILED: {
			Exception e = (Exception) response.getData();
			throw new JRestException("Method invocation failed", e);
		}
		case UNKNOWN_TARGET: {
			Exception e = (Exception) response.getData();
			throw new JRestException("Unknown target/method", e);
		}
		default: {
			throw new JRestException("Unknown status " + response.getStatus());
		}
		}
	}

}
