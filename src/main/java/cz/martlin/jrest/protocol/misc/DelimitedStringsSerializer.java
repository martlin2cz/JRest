package cz.martlin.jrest.protocol.misc;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.QuoteMode;

public class DelimitedStringsSerializer {

	private static final char END_MARKER = '\n';
	private static final char SEPARATOR = ' ';

	private final CSVFormat FORMAT = initializeFormat();

	public DelimitedStringsSerializer() {
	}

	private static CSVFormat initializeFormat() {
		CSVFormat format = CSVFormat.TDF;

		format = format.withDelimiter(SEPARATOR);
		format = format.withIgnoreSurroundingSpaces();
		format = format.withRecordSeparator(END_MARKER);
		format = format.withSkipHeaderRecord();
		format = format.withQuoteMode(QuoteMode.MINIMAL);
		//format = format.withNullString(null);

		return format;
	}

	/**
	 * Converts given list of string to CSV line and returns this line as
	 * string.
	 * 
	 * @param record
	 * @return
	 * @throws IOException
	 */

	public String serialize(List<String> list) throws IOException {
		String line = FORMAT.format(list.toArray());

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
	public List<String> deserialize(String string) throws IOException {
		CSVParser parser = CSVParser.parse(string, FORMAT);
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
