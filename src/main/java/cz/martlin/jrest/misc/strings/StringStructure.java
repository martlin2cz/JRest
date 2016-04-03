package cz.martlin.jrest.misc.strings;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringStructure {
	private final Logger log = LoggerFactory.getLogger(getClass());

	private final Set<String> unpaireds;
	private final Map<String, String> paireds;

	private final Stack<String> pairedStack = new Stack<>();
	private final Set<String> unpairedStack = new HashSet<>();

	private final boolean failOnWarning;

	public StringStructure(Set<String> unpaired, Map<String, String> paired, boolean failOnWarning) {
		this.unpaireds = unpaired;
		this.paireds = paired;

		this.failOnWarning = failOnWarning;
	}

	public boolean isClean() {
		return pairedStack.isEmpty() && unpairedStack.isEmpty();
	}

	public void process(String string) {
		process(0, string);
	}
	
	public void process(int from, String string) {
		for (int i = 0; i < string.length(); i++) {
			int index = from + i;
			for (String unpaired : unpaireds) {
				if (string.startsWith(unpaired, index)) {
					toggleUnpaired(unpaired);
				}
			}

			for (String start : paireds.keySet()) {
				if (string.startsWith(start, index)) {
					addPaired(start);
				}
			}

			for (String end : paireds.values()) {
				if (string.startsWith(end, index)) {
					removePaired(end);
				}
			}
		}
	}

	public void addPaired(String paired) {
		this.pairedStack.push(paired);
	}

	public void removePaired(String paired) {
		String end = paired;

		String shouldStart = this.pairedStack.peek();
		String shouldEnd = this.paireds.get(shouldStart);

		if (!shouldEnd.equals(end)) {
			tryToWarn("Requested " + shouldEnd + " but found " + end);
		}

		this.pairedStack.pop();
	}

	public void addUnpaired(String unpaired) {
		this.unpairedStack.add(unpaired);
	}

	public void removeUnpaired(String unpaired) {
		boolean isThere = this.unpairedStack.contains(unpaired);
		if (!isThere) {
			tryToWarn("Ending not opened " + unpaired);
		}

		this.unpairedStack.remove(unpaired);
	}

	private void toggleUnpaired(String unpaired) {
		if (this.unpairedStack.contains(unpaired)) {
			this.unpairedStack.remove(unpaired);
		} else {
			this.unpairedStack.add(unpaired);
		}
	}

	private void tryToWarn(String message) throws IllegalStateException {
		if (failOnWarning) {
			throw new IllegalStateException(message);
		} else {
			log.warn(message);
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((paireds == null) ? 0 : paireds.hashCode());
		result = prime * result + ((unpaireds == null) ? 0 : unpaireds.hashCode());
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
		StringStructure other = (StringStructure) obj;
		if (paireds == null) {
			if (other.paireds != null)
				return false;
		} else if (!paireds.equals(other.paireds))
			return false;
		if (unpaireds == null) {
			if (other.unpaireds != null)
				return false;
		} else if (!unpaireds.equals(other.unpaireds))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StringStructure [pairedStack=" + pairedStack + ", unpairedStack=" + unpairedStack + "]";
	}

}
