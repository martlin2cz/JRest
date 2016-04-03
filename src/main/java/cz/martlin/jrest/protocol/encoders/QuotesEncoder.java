package cz.martlin.jrest.protocol.encoders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jrest.misc.strings.TokenInfo;
import cz.martlin.jrest.protocol.StringEncoder;

@Deprecated
public class QuotesEncoder implements StringEncoder {
	public static final String DEFAULT_QUOTE_MARK = "\"";

	private final String quote;
	private final String escaped;
	private final String insideRegex;
	private final String inside;
	private final String escapedRegex;

	public QuotesEncoder(String quote) {
		this.quote = quote;

		inside = quote;
		escaped = "\\\\" + quote;
		insideRegex = quote;
		escapedRegex = "\\\\" + quote;
	}

	public QuotesEncoder() {
		this(DEFAULT_QUOTE_MARK);
	}

	@Override
	public String encode(String input) {
		StringBuilder stb = new StringBuilder();

		stb.append(quote);

		String replaced = input.replaceAll(insideRegex, escaped);
		stb.append(replaced);

		stb.append(quote);

		return stb.toString();
	}

	@Override
	public String decode(String input) {
		String naked;

		try {
			naked = checkAndStrip(input);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("String " + input + " cannot be decoded", e);
		}

		String replaced = naked.replaceAll(escapedRegex, inside);
		return replaced;
	}

	private String checkAndStrip(String input) {
		if (!input.startsWith(quote)) {
			throw new IllegalArgumentException("String is not starting with " + quote);
		}

		if (!input.endsWith(quote)) {
			throw new IllegalArgumentException("String is not ending with " + quote);
		}

		int start = quote.length();
		int end = input.length() - quote.length();

		return input.substring(start, end);
	}

	@Override
	public TokenInfo findNext(String input, int from, String separator) {
		String regex = "([^\\\\])([\\\\]{2})*" + quote + separator;
		Pattern pattern = Pattern.compile(regex);

		Matcher match = pattern.matcher(input);

		if (match.find(from)) {
			// + 1 consumed by ^\, + 1 for quote char 
			return TokenInfo.create(from, match.start() + 2, match.end());
		} else {
			return TokenInfo.create(from, input.length());
		}
	}
}
