package cz.martlin.jrest.impl.jarmil.protocol;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jrest.impl.dflt.protocol.DefaultJRestProtocolImpl;
import cz.martlin.jrest.impl.jarmil.handler.JarmilHandler;
import cz.martlin.jrest.impl.jarmil.misc.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.serializers.JarmilShellLikeSerializer;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.protocol.serializer.ReqRespSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Extends Default protocol to be more specific for Jarmil use.
 * 
 * @author martin
 *
 */
public class JarmilProtocol extends DefaultJRestProtocolImpl<JarmilRequest, JarmilResponse> {

<<<<<<< HEAD
	public static final ReqRespSerializer<JarmilRequest, JarmilResponse> SERIALIZER = new JarmilShellLikeSerializer();
=======
	private final JarmilEnvironment environment;
>>>>>>> branch 'master' of https://github.com/martlin2cz/JRest.git

	public JarmilProtocol(int port, String host, JarmilEnvironment environment) {
<<<<<<< HEAD
		super(port, host, SERIALIZER, initHandler(environment));
=======
		super(port, host, initSerializer(environment), initHandler(environment));
		this.environment = environment;
>>>>>>> branch 'master' of https://github.com/martlin2cz/JRest.git
	}

	public JarmilProtocol(int port, JarmilEnvironment environment) {
<<<<<<< HEAD
		super(port, DFLT_HOST, SERIALIZER, initHandler(environment));
=======
		super(port, DFLT_HOST, initSerializer(environment), initHandler(environment));
		this.environment = environment;
	}

	public JarmilEnvironment getEnvironment() {
		return environment;
>>>>>>> branch 'master' of https://github.com/martlin2cz/JRest.git
	}

	public static List<RequestHandler<JarmilRequest, JarmilResponse>> initHandler(JarmilEnvironment environment) {
		List<RequestHandler<JarmilRequest, JarmilResponse>> list = new ArrayList<>(1);

		JarmilHandler handler = new JarmilHandler(environment);
		list.add(handler);

		return list;
	}

	/**
	 * Creates "single" protocol (with only one target, except thoose two defaulting) of given params.
	 * @param port
	 * @param target
	 * @return
	 */
	public static JarmilProtocol createSingle(int port, TargetOnWaiter target) {
		JarmilEnvironment environment = new JarmilEnvironment(target);
		return new JarmilProtocol(port, environment);
	}

	/**
	 * Creates "single" protocol (with only one target, except thoose two defaulting) of given params.
	 * @param port
	 * @param host
	 * @param target
	 * @return
	 */
	public static JarmilProtocol createSingle(int port, String host, TargetOnWaiter target) {
		JarmilEnvironment environment = new JarmilEnvironment(target);
		return new JarmilProtocol(port, host, environment);
	}

}
