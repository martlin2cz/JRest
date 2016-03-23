package cz.martlin.jrest.waiter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.misc.Tools;

/**
 * Encapsulates Java Socket API on server side.
 * 
 * @author martin
 *
 */
public class JRestServer {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private final int port;
	private final ServerSocket server;

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
	 * Starts server
	 * 
	 * @return
	 * @throws JRestException
	 */
	private ServerSocket startServer() throws JRestException {
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			log.debug("Server runing");
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
	public void awaitAndProcess(CommandProcessor processor) {
		Socket sock = null;
		try {
			log.trace("Awaiting command");
			sock = server.accept();

			log.trace("Processing command");
			processCommand(sock, processor);
		} catch (Exception e) {
			log.error("Error during handling of command", e);
		} finally {
			IOUtils.closeQuietly(sock);
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
	private void processCommand(Socket sock, CommandProcessor processor) throws IOException, JRestException {
		InputStream ins = sock.getInputStream();
		String command = Tools.read(ins);
		log.trace("Got command: " + command);

		String response;
		try {
			response = processor.handleCommand(command);
		} catch (Exception e) {
			log.error("Error during process of command: " + command, e);
			response = "";
		}

		log.trace("Sending response: " + response);

		OutputStream ous = sock.getOutputStream();
		Tools.write(ous, response);
	}

	/**
	 * Finishes work of server.
	 */
	public void finishServer() {
		IOUtils.closeQuietly(server);
		log.debug("Server stopped");
	}

}
