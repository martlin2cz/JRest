package cz.martlin.jrest.impl.jarmil;

import cz.martlin.jrest.impl.jarmil.protocol.JarmilGuestProtocol;
import cz.martlin.jrest.impl.jarmil.protocol.JarmilProtocol;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.misc.JRestException;

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
