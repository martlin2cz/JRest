package cz.martlin.jrest.protocol.protocols.simple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.martlin.jrest.protocol.handlers.simple.EchoSimpleCommandHandler;
import cz.martlin.jrest.protocol.handlers.simple.StopWaiterCommandHandler;
import cz.martlin.jrest.protocol.protocols.dflt.DefaultJRestProtocolImpl;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.protocol.serializer.ReqRespSerializer;
import cz.martlin.jrest.protocol.serializers.simple.SimplesShellLikeSerializer;
import cz.martlin.jrest.waiter.RequestHandler;

/**
 * Represents simple protocol. The protocol uses
 * {@link SimplesShellLikeSerializer} (in fact {@link #SERIALIZER}) and adds
 * additional {@link EchoSimpleCommandHandler} and
 * {@link StopWaiterCommandHandler} handlers.
 * 
 * @author martin
 *
 */
public class SimpleJRestProtocolImpl extends DefaultJRestProtocolImpl<SimpleRequest, SimpleResponse> {

	public static final String DFLT_HOST = "localhost";
	public static final ReqRespSerializer<SimpleRequest, SimpleResponse> SERIALIZER = new SimplesShellLikeSerializer();

	public SimpleJRestProtocolImpl(int port, RequestHandler<SimpleRequest, SimpleResponse> handler) {
		super(port, DFLT_HOST, SERIALIZER, init(handler));
	}

	public SimpleJRestProtocolImpl(int port, String host, RequestHandler<SimpleRequest, SimpleResponse> handler) {
		super(port, host, SERIALIZER, init(handler));
	}

	@SafeVarargs
	public SimpleJRestProtocolImpl(int port, RequestHandler<SimpleRequest, SimpleResponse>... handlers) {
		super(port, DFLT_HOST, SERIALIZER, init(handlers));
	}

	@SafeVarargs
	public SimpleJRestProtocolImpl(int port, String host, RequestHandler<SimpleRequest, SimpleResponse>... handlers) {
		super(port, host, SERIALIZER, init(handlers));
	}

	/**
	 * Initializes list of handlers.
	 * 
	 * @param handlers
	 * @return
	 */
	@SafeVarargs
	public static List<RequestHandler<SimpleRequest, SimpleResponse>> init(
			RequestHandler<SimpleRequest, SimpleResponse>... handlers) {
		List<RequestHandler<SimpleRequest, SimpleResponse>> list = list(handlers);

		list.add(new EchoSimpleCommandHandler());
		list.add(new StopWaiterCommandHandler());

		return list;
	}

	@SafeVarargs
	private static List<RequestHandler<SimpleRequest, SimpleResponse>> list(
			RequestHandler<SimpleRequest, SimpleResponse>... handlers) {
		return new ArrayList<>(Arrays.asList(handlers));
	}

}
