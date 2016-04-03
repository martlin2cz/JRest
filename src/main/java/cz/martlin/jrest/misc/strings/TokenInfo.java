package cz.martlin.jrest.misc.strings;

public class TokenInfo {
	private final int tokenStart;
	private final int separatorStart;
	private final int nextTokenStart;

	public TokenInfo(int tokenStart, int separatorStart, int nextTokenStart) {
		super();
		this.tokenStart = tokenStart;
		this.separatorStart = separatorStart;
		this.nextTokenStart = nextTokenStart;
	}

	public int getTokenStart() {
		return tokenStart;
	}

	public int getSeparatorStart() {
		return separatorStart;
	}

	public int getNextTokenStart() {
		return nextTokenStart;
	}

	public int getLength() {
		return nextTokenStart - tokenStart;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + nextTokenStart;
		result = prime * result + separatorStart;
		result = prime * result + tokenStart;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TokenInfo other = (TokenInfo) obj;
		if (nextTokenStart != other.nextTokenStart)
			return false;
		if (separatorStart != other.separatorStart)
			return false;
		if (tokenStart != other.tokenStart)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TokenInfo [tokenStart=" + tokenStart + ", separatorStart=" + separatorStart + ", nextTokenStart="
				+ nextTokenStart + "]";
	}

	/**
	 * 
	 * @param start
	 *            (absolute) token start index
	 * @param separatorStart
	 *            (absolute) separator start index
	 * @param separatorEnd
	 *            (absolute) separator end index
	 * @return
	 */
	public static TokenInfo create(int start, int separatorStart, int separatorEnd) {
		return new TokenInfo(start, /* start + */ separatorStart, /* start + */separatorEnd);
		// TODO like that?
	}

	/**
	 * 
	 * @param start
	 *            (absolute) token start index
	 * @param end
	 *            (absolute) token end index (no separator exists)
	 * @return
	 */
	public static TokenInfo create(int start, int end) {
		return new TokenInfo(start, end, end);
		// TODO like that?
	}

}