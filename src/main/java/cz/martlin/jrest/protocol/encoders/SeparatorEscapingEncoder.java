package cz.martlin.jrest.protocol.encoders;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cz.martlin.jrest.misc.strings.TokenInfo;
import cz.martlin.jrest.protocol.StringEncoder;

@Deprecated
public class SeparatorEscapingEncoder implements StringEncoder {

	public static final String ESCAPE = "E"; // "\\\\";
	// private static final String ESCAPE_REGEX = "(?<=(([^" + ESCAPE + "]|^)("
	// + ESCAPE + "{2}){0,100}))" + ESCAPE + "";
	// private static final String UNESCAPE_REGEX = "(?<=(([^" + ESCAPE +
	// "]|^)(" + ESCAPE + "{2}){0,100}))";

	// private static final String ESCAPE_REGEX = "(([^" + ESCAPE + "]?)([" +
	// ESCAPE + "]{2})*" + ESCAPE + ")";
	// private static final String UNESCAPE_REGEX = "(([^" + ESCAPE + "]?)([" +
	// ESCAPE + "]{2})*" + ")";

	private final String sepRegex;
	private final Pattern sepPattern;
	private final String sepReplacement;

	private final String notsepRegex;
	private final Pattern notsepPattern;
	private final String notsepReplacement;

	public SeparatorEscapingEncoder(String separatorAsRegex) {
		this.sepRegex = regexOfUnescaped(separatorAsRegex);
		this.sepPattern = Pattern.compile(sepRegex);
		this.sepReplacement = ESCAPE + "{}";// "$4";// "$5";//"<$2>" + ESCAPE +
											// "<$5>";

		this.notsepRegex = regexOfEscaped(separatorAsRegex);
		this.notsepPattern = Pattern.compile(notsepRegex);
		this.notsepReplacement = "{}";// "$5";// "$2$5";//"<$2><$5>";
	}

	private String regexOfEscaped(String ofWhat) {
		return "(?<=(^|[^" + ESCAPE + "])((" + ESCAPE + "{2}){0,100}))" + //
				"(" + ESCAPE + ")" + //
				"(" + ofWhat + ")";
	}

	private String regexOfUnescaped(String ofWhat) {
		return "(?<=(^|[^" + ESCAPE + "])((" + ESCAPE + "{2}){0,100}))" + //
				"" + //
				"(" + ofWhat + ")";
	}

	protected boolean matchesSeparator(String input) {
		return sepPattern.matcher(input).find();
	}

	protected boolean matchesNotSeparator(String input) {
		return notsepPattern.matcher(input).find();
	}

	@Override
	public String encode(String input) {
		Matcher escMatch = notsepPattern.matcher(input);
		String escOutput = escMatch.replaceAll(sepReplacement);

		Matcher biescMatch = sepPattern.matcher(escOutput);
		String biescOutput = biescMatch.replaceAll(sepReplacement);

		return biescOutput;
	}

	@Override
	public String decode(String input) {
		Matcher match = notsepPattern.matcher(input);
		return match.replaceAll(notsepReplacement);
	}

	@Override
	public TokenInfo findNext(String input, int from, String separator) {
		Matcher match = sepPattern.matcher(input);

		if (match.find(from)) {
			return TokenInfo.create(from, match.start() + 1, match.end());
		} else {
			return TokenInfo.create(from, input.length());
		}
	}

}
