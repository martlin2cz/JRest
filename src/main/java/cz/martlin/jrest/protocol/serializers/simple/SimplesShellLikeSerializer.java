package cz.martlin.jrest.protocol.serializers.simple;

import java.util.ArrayList;
import java.util.List;

import cz.martlin.jrest.protocol.reqresps.simple.ResponseStatus;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.protocol.serializers.BaseShellLikeSerializer;

/**
 * Represents serializer with syntax simmilar to unix shells. All data is
 * printed to one line, separated by {@link #SEPARATOR} ended by
 * {@link #END_MARKER}.
 * 
 * <pre>
 * Request format: 
 * [command]{@value #SEPARATOR}[arg1]{@value #SEPARATOR}[arg2]{@value #SEPARATOR}...{@value #END_MARKER}
 * 
 * Response format:
 * [status]{@value #SEPARATOR}[meta]{@value #SEPARATOR}[data]{@value #END_MARKER}
 * </pre>
 * 
 * @author martin
 *
 */
public class SimplesShellLikeSerializer extends BaseShellLikeSerializer<SimpleRequest, SimpleResponse> {

	public SimplesShellLikeSerializer() {
		super();
	}

	@Override
	protected SimpleRequest listToRequest(List<String> list) throws IllegalArgumentException {
		if (list.size() < 1) {
			throw new IllegalArgumentException("Missing command name or sent empty request in " + list);
		}

		List<String> parameters = list;

		return new SimpleRequest(parameters);
	}

	@Override
	protected SimpleResponse listToResponse(List<String> list) throws IllegalArgumentException {
		if (list.size() != 3) {
			throw new IllegalArgumentException("Response need to have three parts, but have " + list.size());
		}

		// status
		String first = list.get(0);
		ResponseStatus status;
		try {
			status = ResponseStatus.valueOf(first);
		} catch (Exception e) {
			throw new IllegalArgumentException("Uknown status " + first);
		}

		// meta
		String second = list.get(1);
		String meta = second;

		// data
		String third = list.get(2);
		String data = third;

		return new SimpleResponse(status, data, meta);
	}

	@Override
	protected List<String> requestToList(SimpleRequest request) {
		List<String> list = new ArrayList<>(request.getArguments().size() + 1);

		list.add(request.getCommand());
		list.addAll(request.getArguments());

		return list;
	}

	@Override
	protected List<String> responseToList(SimpleResponse response) {
		List<String> list = new ArrayList<>(3);

		list.add(response.getStatus().name());
		list.add(response.getMeta());
		list.add(response.getData());

		return list;
	}

}
