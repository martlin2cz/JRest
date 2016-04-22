package cz.martlin.jrest.waiter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.WaiterProtocol;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;
import cz.martlin.jrest.protocol.reqresp.RequestSerializer;
import cz.martlin.jrest.protocol.reqresp.ResponseSerializer;
import cz.martlin.jrest.protocol.reqresps.simple.ResponseStatus;

/**
 * Implements work of deserialization of requests, processing and serialization
 * of responses. And related stuff.
 * 
 * @author martin
 *
 */
public class RequestsProcessor<RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final RequestSerializer<RQT> requestSerializer;
	private final ResponseSerializer<RST> responseSerializer;
	private final List<RequestHandler<RQT, RST>> handlers;

	/**
	 * Creates instance of given params.
	 * 
	 * @param requestSerializer
	 * @param responseSerializer
	 * @param handlers
	 */
	public RequestsProcessor(RequestSerializer<RQT> requestSerializer, ResponseSerializer<RST> responseSerializer,
			List<RequestHandler<RQT, RST>> handlers) {
		super();
		this.requestSerializer = requestSerializer;
		this.responseSerializer = responseSerializer;
		this.handlers = handlers;
	}

	/**
	 * Runs initialization on all handlers.
	 * 
	 * @param waiter
	 */
	public void initialize(JRestWaiter<RQT, RST> waiter) {
		log.trace("Initializing");
		for (RequestHandler<RQT, RST> handler : handlers) {
			try {
				handler.initialize(waiter);
			} catch (Exception e) {
				log.error("Cannot initialize handler " + handler, e);
			}
		}
		log.trace("Initialized");
	}

	/**
	 * Runs finish on all handlers.
	 * 
	 * @param waiter
	 */
	public void finish(JRestWaiter<RQT, RST> waiter) {
		log.trace("Finishing");
		for (RequestHandler<RQT, RST> handler : handlers) {
			try {
				handler.finish(waiter);
			} catch (Exception e) {
				log.error("Cannot finish handler " + handler, e);
			}
		}
		log.trace("Finished");
	}

	/**
	 * Processes encoded request. Returns encoded response. If fails, tries to
	 * send encoded "FATAL" response. If this fail, sends simple error message
	 * as string.
	 * 
	 * @param request
	 * @return
	 */
	public String processWithEncoding(String request) {
		try {
			log.trace("Deserializing request: {}", request);
			RQT req = requestSerializer.deserializeRequest(request);

			log.debug("Processing request: {}", req);
			RST resp = process(req);

			log.trace("Serializing response: {}", resp);
			String response = responseSerializer.serializeResponse(resp);

			log.trace("Sending back response: {}", response);
			return response;
		} catch (JRestException e) {
			log.error("An unahndled error occured during the handling of request: " + request, e);
			return tryToSerializeAsFatal(e);
		}
	}

	/**
	 * Tries to make from given exception the "FATAL" response and serialize. If
	 * failed, returns simple string error message.
	 * 
	 * @param exception
	 * @return
	 */
	private String tryToSerializeAsFatal(JRestException exception) {
		String message = "An error occured during the request processing. " + "The exception: " + exception.toString()
				+ ", caused by " + exception.toString();

		RST response = null; // TODO FIXME JRestDefaultResponse.fatal(message);
		try {
			return responseSerializer.serializeResponse(response);
		} catch (JRestException e) {
			String error = ResponseStatus.FATAL + ": " + message;
			return error;
		}
	}

	/**
	 * Processes given request with the first matching handler (handler which's
	 * handle method does not return null). Returns response.
	 * 
	 * @param request
	 * @return
	 * @throws JRestException
	 */
	private RST process(RQT request) throws JRestException {
		for (RequestHandler<RQT, RST> handler : handlers) {
			try {
				RST response = handler.handle(request);
				if (response != null) {
					return response;
				}
			} catch (Exception e) {
				throw new JRestException("An exception occured during the request handling", e);
			}
		}

		throw new JRestException("Found no handler for request: " + request);
		// TODO like this?
		// return DEFAULT_HANDLER.handleQuietly(request);
	}

	/**
	 * Creates processor from the given protocol.
	 * 
	 * @param protocol
	 * @return
	 */
	public static <RQT extends JRestAbstractRequest, RST extends JRestAbstractResponse> //
	RequestsProcessor<RQT, RST> create(WaiterProtocol<RQT, RST> protocol) {
		return new RequestsProcessor<RQT, RST>(//
				protocol.getRequestDeserializer(), //
				protocol.getReponseSerializer(), //
				protocol.getHandlers());
	}

}
