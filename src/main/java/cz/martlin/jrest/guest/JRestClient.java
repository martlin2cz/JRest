package cz.martlin.jrest.guest;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.misc.Tools;

public class JRestClient {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final int port;
	private final String host;

	public JRestClient(int port, String host) {
		super();
		this.port = port;
		this.host = host;
	}

	public String send(String string) throws JRestException {
		Socket sock = null;

		try {
			log.trace("Sending command: " + string);
			sock = new Socket(host, port);

			OutputStream ous = sock.getOutputStream();
			Tools.write(ous, string);

			InputStream ins = sock.getInputStream();
			String response = Tools.read(ins);

			log.trace("Got response: " + response);
			return response;
		} catch (Exception e) {
			throw new JRestException("Cannot send command", e);
		} finally {
			IOUtils.closeQuietly(sock);
		}
	}

}
