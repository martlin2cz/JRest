package cz.martlin.jrest.misc.strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExtRegex {

	private final Pattern pattern;

	public ExtRegex(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public RegexMatchInfo findFirst(int start, String input) {
		Matcher match = pattern.matcher(input);

		if (match.find(start)) {
			return new RegexMatchInfo(match.start(), match.end());
		} else {
			return null;
		}
	}

	public String replaceAll(int start, String input, String replacement) {
		return replaceAll(start, input, replacement, 0);
	}

	public String replaceAll(int start, String input, String replacement, int rollback) {
		Matcher match = pattern.matcher(input);

		String begin = input.substring(0, start);
		StringBuilder result = new StringBuilder(begin);

		int i = start;
		while (i < input.length()) {
			boolean found = match.find(i);
			if (found) {
				String skipped = findSkipped(i, input, match);
				result.append(skipped);

				String replaced = replaceMatch(pattern, input, match, replacement);
				result.append(replaced);

				int newI = match.end() - rollback;
				if (newI == i) {
					newI++;
				}
				i = newI;
			} else {
				String rest = findRest(i, input);
				result.append(rest);
				break;
			}
		}

		return result.toString();
	}

	private String findRest(int from, String input) {
		String substr = input.substring(from);
		return substr;
	}

	private String findSkipped(int current, String input, Matcher match) {
		String substr = input.substring(current, match.start());
		return substr;
	}

	private String replaceMatch(Pattern pattern, String input, Matcher match, String replacement) {
		String substr = input.substring(match.start(), match.end());
		Matcher rematch = pattern.matcher(substr);
		return rematch.replaceFirst(replacement);
	}

}
