package cz.martlin.jrest.protocol.encoders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jrest.protocol.misc.StringEncoder;
import cz.martlin.jrest.protocol.misc.TokenInfo;

/**
 * The basic encoder - does nothing during the serialization (and the
 * deserialization as well).
 * 
 * <strong>Warning: The encoded and decoded text must not contain the
 * separator!!!</strong>
 * 
 * @author martin
 *
 */
public class NoopEncoder implements StringEncoder {

	// TODO warn if encoded input matches separator regex?

	public NoopEncoder() {
	}

	@Override
	public String encode(String input) throws Exception {
		return input;
	}

	@Override
	public String decode(String input) throws Exception {
		return input;
	}

	@Override
	public TokenInfo findNext(String input, int from, String separator) throws Exception {
		Pattern pattern = Pattern.compile(separator);
		Matcher match = pattern.matcher(input);

		if (match.find(from)) {
			return TokenInfo.create(from, match.start(), match.end());
		} else {
			return TokenInfo.create(from, input.length());
		}
	}

}
