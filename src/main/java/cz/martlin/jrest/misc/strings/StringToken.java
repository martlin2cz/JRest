package cz.martlin.jrest.misc.strings;

public class StringToken extends TokenInfo {
	private final String token;
	private final String separator;

	public StringToken(int start, String token, String separator) {
		super(start, start + token.length(), start + token.length() + separator.length());

		this.token = token;
		this.separator = separator;
	}

	public String getToken() {
		return token;
	}

	public String getSeparator() {
		return separator;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((separator == null) ? 0 : separator.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		StringToken other = (StringToken) obj;
		if (separator == null) {
			if (other.separator != null)
				return false;
		} else if (!separator.equals(other.separator))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StringToken [token=" + token + ", separator=" + separator + ", getTokenStart()=" + getTokenStart()
				+ "]";
	}

	public static StringToken create(String input, TokenInfo info) {
		int start = info.getTokenStart();

		String token = input.substring(info.getTokenStart(), info.getSeparatorStart());
		String separator = input.substring(info.getSeparatorStart(), info.getNextTokenStart());

		return new StringToken(start, token, separator);
	}


}
