package cz.martlin.jrest.protocol.serializers;

import java.util.Iterator;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.JRestResponse;
import cz.martlin.jrest.protocol.StringEncoder;

public class SimpleSerializerImpl implements ReqRespSerializer {

	private static final String SEPARATOR = " ";

	private final StringEncoder encoder;

	public SimpleSerializerImpl(StringEncoder encoder) {
		this.encoder = encoder;
	}

	@Override
	public String serializeResponse(JRestResponse response) throws JRestException {
		StringBuilder stb = new StringBuilder();

		stb.append(response.getStatus());

		stb.append(SEPARATOR);

		try {
			String data = encoder.encode(response.getData());
			stb.append(data);
		} catch (Exception e) {
			throw new JRestException("Cannot encode response data", e);
		}

		stb.append(SEPARATOR);

		try {
			String meta = encoder.encode(response.getMeta());
			stb.append(meta);
		} catch (Exception e) {
			throw new JRestException("Cannot encode response meta", e);
		}

		return stb.toString();
	}

	@Override
	public JRestResponse deserializeResponse(String serialized) {
		// TODO split
		return null;
	}

	@Override
	public String serializeRequest(JRestRequest request) throws JRestException {
		StringBuilder stb = new StringBuilder();

		stb.append(request.getCommand());
		stb.append(SEPARATOR);

		Iterator<String> iter = request.getArguments().iterator();
		while (iter.hasNext()) {
			String argument = iter.next();

			try {
				String encoded = encoder.encode(argument);
				stb.append(encoded);
			} catch (Exception e) {
				throw new JRestException("Cannot encode argument" + argument, e);
			}

			if (iter.hasNext()) {
				stb.append(SEPARATOR);
			}
		}

		return stb.toString();
	}

	@Override
	public JRestRequest deserializeRequest(String serialized) {
		// TODO split
		return null;
	}

}
