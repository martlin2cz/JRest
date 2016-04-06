package cz.martlin.jrest.protocol.protocols.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jrest.protocol.handlers.EchoCommandHandler;
import cz.martlin.jrest.protocol.handlers.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.protocols.dflt.DefaultJRestProtocolImpl;
import cz.martlin.jrest.protocol.serializers.ReqRespSerializer;
import cz.martlin.jrest.protocol.serializers.ShellLikeSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Represents simple protocol. The protocol uses {@link ShellLikeSerializer} (in
 * fact {@link #SERIALIZER}) and adds additional {@link EchoCommandHandler} and
 * {@link StopWaiterCommandHandler} handlers.
 * 
 * @author martin
 *
 */
public class SimpleJRestProtocolImpl extends DefaultJRestProtocolImpl {

	public static final String DFLT_HOST = "localhost";
	public static final ReqRespSerializer SERIALIZER = new ShellLikeSerializer();

	public SimpleJRestProtocolImpl(int port, RequestHandler handler) {
		super(port, DFLT_HOST, SERIALIZER, init(handler));
	}

	public SimpleJRestProtocolImpl(int port, String host, RequestHandler handler) {
		super(port, host, SERIALIZER, init(handler));
	}

	public SimpleJRestProtocolImpl(int port, RequestHandler... handlers) {
		super(port, DFLT_HOST, SERIALIZER, init(handlers));
	}

	public SimpleJRestProtocolImpl(int port, String host, RequestHandler... handlers) {
		super(port, host, SERIALIZER, init(handlers));
	}

	/**
	 * Initializes list of handlers.
	 * 
	 * @param handlers
	 * @return
	 */
	public static List<RequestHandler> init(RequestHandler... handlers) {
		List<RequestHandler> list = list(handlers);

		list.add(new EchoCommandHandler());
		list.add(new StopWaiterCommandHandler());

		return list;
	}

	private static List<RequestHandler> list(RequestHandler... handlers) {
		return new ArrayList<>(Arrays.asList(handlers));
	}

}
