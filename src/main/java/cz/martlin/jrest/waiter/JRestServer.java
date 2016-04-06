package cz.martlin.jrest.waiter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.Interruptable;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.misc.Tools;

/**
 * Encapsulates Java Socket API on server side.
 * 
 * @author martin
 *
 */
public class JRestServer implements Interruptable {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final int port;
	private final ServerSocket server;

	private boolean interrupted;

	/**
	 * Creates and starts server listening on given port.
	 * 
	 * @param port
	 * @throws JRestException
	 */
	public JRestServer(int port) throws JRestException {
		super();
		this.port = port;

		this.server = startServer();

	}

	/**
	 * Starts server.
	 * 
	 * @return
	 * @throws JRestException
	 */
	private ServerSocket startServer() throws JRestException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			log.info("Server runing");
			return server;
		} catch (IOException e) {
			throw new JRestException("Cannot start server", e);
		}
	}

	/**
	 * Waits to next connection. Then processes the incame request with given
	 * processor.
	 * 
	 * @param processor
	 */
	public void awaitAndProcess(RequestsProcessor processor) {
		Socket sock = null;
		try {
			log.trace("Awaiting request");
			sock = server.accept();

			if (checkAndHandleInterrupted(sock)) {
				return;
			}

			log.trace("Request arrived");
			processRequest(sock, processor);
		} catch (Exception e) {
			log.error("Error during handling of request", e);
		} finally {
			IOUtils.closeQuietly(sock);
		}
	}

	/**
	 * Check if the server is not yet interrupted. If yes, logs and very very
	 * very simply writes some shit (okay, just empty string) into response and
	 * returns.
	 * 
	 * @param sock
	 * @return true if have been interrupted, false if not
	 * @throws IOException
	 * @throws JRestException
	 */
	private boolean checkAndHandleInterrupted(Socket sock) throws IOException, JRestException {
		if (interrupted) {
			log.debug("Server is interrupted, the recieved request is ignored and response is beeing send empty");

			OutputStream ous = sock.getOutputStream();
			Tools.write(ous, "");
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Processes given command with given processor.
	 * 
	 * @param sock
	 * @param processor
	 * @throws IOException
	 * @throws JRestException
	 */
	private void processRequest(Socket sock, RequestsProcessor processor) throws IOException, JRestException {
		InputStream ins = sock.getInputStream();
		String request = Tools.read(ins);
		log.debug("Processing request: " + request);

		String response;
		try {
			response = processor.processWithEncoding(request);
		} catch (Exception e) {
			log.error("Error during process of command: " + request, e);
			response = "";
		}

		log.debug("Sending response: " + response);

		OutputStream ous = sock.getOutputStream();
		Tools.write(ous, response);
	}

	/**
	 * Finishes work of server.
	 */
	public void finishServer() {
		IOUtils.closeQuietly(server);
		log.info("Server stopped");
	}

	@Override
	public void interrupt() {
		this.interrupted = true;
	}
}
