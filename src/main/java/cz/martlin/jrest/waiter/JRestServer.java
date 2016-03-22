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

public class JRestServer {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final int port;

	private final ServerSocket server;

	public JRestServer(int port) throws JRestException {
		super();
		this.port = port;

		this.server = startServer();

	}

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

	public void finishServer() {
		IOUtils.closeQuietly(server);
		log.debug("Server stopped");
	}

}
