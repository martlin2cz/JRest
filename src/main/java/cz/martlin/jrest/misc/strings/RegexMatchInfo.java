package cz.martlin.jrest.misc.strings;

public class RegexMatchInfo {
	private final int matchStart;
	private final int matchEnd;

	public RegexMatchInfo(int matchStart, int matchEnd) {
		super();
		this.matchStart = matchStart;
		this.matchEnd = matchEnd;
	}

	public int getMatchStart() {
		return matchStart;
	}

	public int getMatchEnd() {
		return matchEnd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + matchEnd;
		result = prime * result + matchStart;
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
		RegexMatchInfo other = (RegexMatchInfo) obj;
		if (matchEnd != other.matchEnd)
			return false;
		if (matchStart != other.matchStart)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RegexMatchInfo [matchStart=" + matchStart + ", matchEnd=" + matchEnd + "]";
	}

}
