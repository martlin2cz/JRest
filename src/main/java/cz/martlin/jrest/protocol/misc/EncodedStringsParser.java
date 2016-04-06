package cz.martlin.jrest.protocol.misc;

import java.util.LinkedList;
import java.util.List;

import cz.martlin.jrest.misc.JRestException;

/**
 * Class which with help of {@link StringEncoder#findNext(String, int, String)}
 * parses input to list of parts.
 * 
 * @author martin
 *
 */
public class EncodedStringsParser {

	private final StringEncoder encoder;
	private final String separator;

	public EncodedStringsParser(StringEncoder encoder, String separator) {
		super();
		this.encoder = encoder;
		this.separator = separator;
	}

	/**
	 * Parses given input and splits into particular parts.
	 * 
	 * @param input
	 * @return
	 * @throws JRestException
	 */
	public List<String> parse(String input) throws JRestException {
		List<String> result = new LinkedList<>();
		int current = 0;

		while (current < input.length()) {
			try {
				TokenInfo info = encoder.findNext(input, current, separator);
				String part = input.substring(info.getTokenStart(), info.getSeparatorStart());
				result.add(part);

				current = info.getNextTokenStart();
			} catch (Exception e) {
				throw new JRestException("Cannot parse part of encoded string starting at " + current, e);
			}
		}

		return result;
	}
}
