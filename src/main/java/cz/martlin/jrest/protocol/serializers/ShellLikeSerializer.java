package cz.martlin.jrest.protocol.serializers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;

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
public class ShellLikeSerializer implements ReqRespSerializer {

	private static final char END_MARKER = '\n';
	private static final char SEPARATOR = ' ';

	private final CSVFormat FORMAT = initializeFormat();

	public ShellLikeSerializer() {
	}

	private static CSVFormat initializeFormat() {
		CSVFormat format = CSVFormat.TDF;

		format = format.withDelimiter(SEPARATOR);
		format = format.withIgnoreSurroundingSpaces();
		format = format.withRecordSeparator(END_MARKER);
		format = format.withSkipHeaderRecord();
		format = format.withQuoteMode(QuoteMode.MINIMAL);

		return format;
	}

	@Override
	public String serializeRequest(JRestRequest request) throws JRestException {
		List<String> record = new ArrayList<>(request.getArguments().size() + 1);

		record.add(request.getCommand());
		record.addAll(request.getArguments());

		try {
			return listToCSVstring(record);
		} catch (IOException e) {
			throw new JRestException("Cannot print request, sorry...", e);
		}
	}

	@Override
	public JRestRequest deserializeRequest(String serialized) throws JRestException {
		List<String> parts;
		try {
			parts = csvStringToRecord(serialized);
		} catch (IOException e) {
			throw new JRestException("Cannot parse request" + serialized + ", sorry...", e);
		}

		if (parts.size() < 1) {
			throw new JRestException("Missing command name or sent empty request in " + serialized);
		}

		List<String> parameters = parts;

		return new JRestRequest(parameters);

	}

	@Override
	public String serializeResponse(JRestResponse response) throws JRestException {
		List<String> record = new ArrayList<>(3);

		record.add(response.getStatus().name());
		record.add(response.getMeta());
		record.add(response.getData());

		try {
			return listToCSVstring(record);
		} catch (IOException e) {
			throw new JRestException("Cannot print response, sorry...", e);
		}
	}

	@Override
	public JRestResponse deserializeResponse(String serialized) throws JRestException {
		List<String> parts;
		try {
			parts = csvStringToRecord(serialized);
		} catch (IOException e1) {
			throw new JRestException("Cannot parse response " + serialized + ", sorry...");
		}

		if (parts.size() != 3) {
			throw new JRestException("Response need to have three parts, but have " + parts.size());
		}

		// status
		String first = parts.get(0);
		ResponseStatus status;
		try {
			status = ResponseStatus.valueOf(first);
		} catch (Exception e) {
			throw new JRestException("Uknown status " + first);
		}

		// meta
		String second = parts.get(1);
		String meta = second;

		// data
		String third = parts.get(2);
		String data = third;

		return new JRestResponse(status, data, meta);
	}

	/**
	 * Converts given list of string to CSV line and returns this line as
	 * string.
	 * 
	 * @param record
	 * @return
	 * @throws IOException
	 */
	protected String listToCSVstring(List<String> record) throws IOException {
		String line = FORMAT.format(record.toArray());

		String result = line + FORMAT.getRecordSeparator();

		return result;
	}

	/**
	 * Parses given string representing CSV line into particular fields.
	 * 
	 * @param csv
	 * @return
	 * @throws IOException
	 */
	protected List<String> csvStringToRecord(String csv) throws IOException {
		CSVParser parser = CSVParser.parse(csv, FORMAT);
		List<CSVRecord> records = parser.getRecords();

		if (records.size() != 1) {
			throw new IOException("Missing or excessing lines count, expected 1 found " + records.size());
		}

		CSVRecord record = records.get(0);
		return list(record);
	}

	/**
	 * Converts given {@link CSVRecord} to list.
	 * 
	 * @param record
	 * @return
	 */
	protected static List<String> list(CSVRecord record) {
		List<String> list = new LinkedList<>();

		for (String item : record) {
			list.add(item);
		}

		return list;
	}

}
