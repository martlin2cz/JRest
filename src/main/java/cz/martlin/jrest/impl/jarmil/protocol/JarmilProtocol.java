package cz.martlin.jrest.impl.jarmil.protocol;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jrest.impl.dflt.protocol.DefaultJRestProtocolImpl;
import cz.martlin.jrest.impl.jarmil.handlers.JarmilHandler;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.serializers.JarmilShellLikeSerializer;
import cz.martlin.jrest.protocol.serializer.ReqRespSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Extends Default protocol to be more specific for Jarmil use.
 * 
 * @author martin
 *
 */
public class JarmilProtocol extends DefaultJRestProtocolImpl<JarmilRequest, JarmilResponse> {

	private final JarmilEnvironment environment;

	public JarmilProtocol(int port, String host, JarmilEnvironment environment) {
		super(port, host, initSerializer(environment), initHandler(environment));
		this.environment = environment;
	}

	public JarmilProtocol(int port, JarmilEnvironment environment) {
		super(port, DFLT_HOST, initSerializer(environment), initHandler(environment));
		this.environment = environment;
	}

	public JarmilEnvironment getEnvironment() {
		return environment;
	}

	private static ReqRespSerializer<JarmilRequest, JarmilResponse> initSerializer(JarmilEnvironment environment) {
		return new JarmilShellLikeSerializer(environment);
	}

	private static List<RequestHandler<JarmilRequest, JarmilResponse>> initHandler(JarmilEnvironment environment) {
		List<RequestHandler<JarmilRequest, JarmilResponse>> list = new ArrayList<>(1);

		JarmilHandler handler = new JarmilHandler(environment);
		list.add(handler);

		return list;
	}

}
