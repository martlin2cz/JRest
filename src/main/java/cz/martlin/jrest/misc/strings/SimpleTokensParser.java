package cz.martlin.jrest.misc.strings;

import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SimpleTokensParser implements Iterator<StringToken> {
	private final String input;
	private final Pattern pattern;
	private final Pattern escaped;

	private int nextStartingAt;

	public SimpleTokensParser(String input, String separator, String escapedSeparator) {
		super();
		this.input = input;
		this.pattern = Pattern.compile(separator);
		this.escaped = Pattern.compile(escapedSeparator);
	}

	@Override
	public boolean hasNext() {
		return nextStartingAt < input.length();
	}

	@Override
	public StringToken next() {
		TokenInfo info = findNext(nextStartingAt);

		nextStartingAt = info.getNextTokenStart();

		return StringToken.create(input, info);
	}

	protected TokenInfo findNext(int start) {
		String remaining = input.substring(start);

		//TODO 
//		Matcher matcher;
//		while (xx) {
//			Matcher matcher = escaped.matcher(remaining);
//			
//		}
		
		
		Matcher matcher = pattern.matcher(remaining);
		int sot;
		int eot;

		if (matcher.find()) {
			sot = matcher.start();
			eot = matcher.end();
		} else {
			sot = remaining.length();
			eot = remaining.length();
		}

		return new TokenInfo(start, start + sot, start + eot);
	}
}
