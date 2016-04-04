package cz.martlin.jrest.protocol.serializers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.JRestRequest;
import cz.martlin.jrest.protocol.JRestResponse;
import cz.martlin.jrest.protocol.ResponseStatus;
import cz.martlin.jrest.protocol.StringEncoder;
import cz.martlin.jrest.protocol.misc.EncodedStringsParser;

/**
 * The simple serializer. The serializer uses following format:
 * 
 * <pre>
 * Request format: 
 * [command]{@link #separator}[arg1]{@link #separator}[arg2]{@link #separator}...
 * 
 * Response format: 
 * [status]{@link #separator}[meta]{@link #separator}[data]
 * </pre>
 * 
 * where the each item (in [ ]) is additionally encoded by {@link #encoder}.
 * 
 * @author martin
 *
 */
public class SimpleSerializerImpl implements ReqRespSerializer {

	private final StringEncoder encoder;
	private final String separator;
	private final EncodedStringsParser parser;

	public SimpleSerializerImpl(StringEncoder encoder, String separator) {
		this.encoder = encoder;
		this.separator = separator;

		this.parser = new EncodedStringsParser(encoder, separator);
	}

	@Override
	public String serializeResponse(JRestResponse response) throws JRestException {
		StringBuilder stb = new StringBuilder();

		stb.append(response.getStatus());

		stb.append(separator);

		try {
			String meta = encoder.encode(response.getMeta());
			stb.append(meta);
		} catch (Exception e) {
			throw new JRestException("Cannot encode response meta", e);
		}

		stb.append(separator);

		try {
			String data = encoder.encode(response.getData());
			stb.append(data);
		} catch (Exception e) {
			throw new JRestException("Cannot encode response data", e);
		}

		return stb.toString();
	}

	@Override
	public JRestResponse deserializeResponse(String serialized) throws JRestException {
		List<String> parts = parser.parse(serialized);

		if (parts.size() != 3) {
			throw new JRestException("Response need to have three parts, but have " + parts.size());
		}

		// status
		String first = parts.get(0);
		ResponseStatus status;
		try {
			status = ResponseStatus.valueOf(encoder.decode(first));
		} catch (Exception e) {
			throw new JRestException("Uknown status " + first);
		}

		// meta
		String second = parts.get(1);
		String meta;
		try {
			meta = encoder.decode(second);
		} catch (Exception e) {
			throw new JRestException("Cannot decode meta " + second);
		}

		// data
		String third = parts.get(2);
		String data;
		try {
			data = encoder.decode(third);
		} catch (Exception e) {
			throw new JRestException("Cannot decode data " + third);
		}

		return new JRestResponse(status, data, meta);
	}

	@Override
	public String serializeRequest(JRestRequest request) throws JRestException {
		StringBuilder stb = new StringBuilder();

		stb.append(request.getCommand());
		stb.append(separator);

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
				stb.append(separator);
			}
		}

		return stb.toString();
	}

	@Override
	public JRestRequest deserializeRequest(String serialized) throws JRestException {
		List<String> parts = parser.parse(serialized);

		if (parts.size() < 1) {
			throw new JRestException("Missing command name or sent empty request in " + serialized);
		}

		List<String> parameters = new ArrayList<>(parts.size() - 1);

		for (String encoded : parts) {
			try {
				String decoded = encoder.decode(encoded);
				parameters.add(decoded);
			} catch (Exception e) {
				throw new JRestException("Cannot decode parameter " + encoded);
			}
		}

		return new JRestRequest(parameters);
	}

}
