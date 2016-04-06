package cz.martlin.jrest.protocol.misc;

/**
 * Represents information about some token followed (if so) by some separator.
 * For example, if we have string <code>foo||bar</code>, the infos for tokens
 * will look like:
 * 
 * <pre>
 * new TokenInfo(0, 3, 5)	//for token "foo"
 * new TokenInfo(5, 8, 8)	//for token "bar"
 * </pre>
 * 
 * or
 * 
 * <pre>
 * TokenInfo.create(0, 3, 5)	//for token "foo"
 * TokenInfo.create(5, 8)		//for token "bar"
 * </pre>
 * 
 * @author martin
 *
 */
public class TokenInfo {
	private final int tokenStart;
	private final int separatorStart;
	private final int nextTokenStart;

	/**
	 * Creates token info instance with given params.
	 * 
	 * @param tokenStart
	 * @param separatorStart
	 * @param nextTokenStart
	 */
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
	 * Creates instance (with separator) of given params.
	 * 
	 * @param start
	 *            (absolute) token start index
	 * @param separatorStart
	 *            (absolute) separator start index
	 * @param separatorEnd
	 *            (absolute) separator end index (ehm, in fact index of next
	 *            token start)
	 * @return
	 */
	public static TokenInfo create(int start, int separatorStart, int separatorEnd) {
		return new TokenInfo(start, separatorStart, separatorEnd);
	}

	/**
	 * Creates instance (with no separator) of given params.
	 * 
	 * @param start
	 *            (absolute) token start index
	 * @param end
	 *            (absolute) token end index
	 * @return
	 */
	public static TokenInfo create(int start, int end) {
		return new TokenInfo(start, end, end);
	}

}