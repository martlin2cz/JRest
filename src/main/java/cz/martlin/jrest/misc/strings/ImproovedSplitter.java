package cz.martlin.jrest.misc.strings;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ImproovedSplitter implements Iterator<String> {

	private final String input;
	private final Set<String> unpaired;
	private final Map<String, String> paired;

	private final SimpleTokensParser simple;
	private final boolean failOnWarning = true; // TODO vymyslet

	private int nextTokenStart;

	public ImproovedSplitter(String separator, String input, Set<String> unpaired, Map<String, String> paired) {
		this.simple = new SimpleTokensParser(input, separator, null);	//TODO
		this.input = input;

		this.unpaired = unpaired;
		this.paired = paired;
	}

	@Override
	public boolean hasNext() {
		return simple.hasNext();
	}

	@Override
	public String next() {
		StringStructure struct = new StringStructure(unpaired, paired, failOnWarning);

		int start = nextTokenStart;
		int end = start;

		do {
			StringToken token = simple.next();
			struct.process(token.getToken());
			end += token.getLength(); // FIXME: appends the separator part as
										// well, need to be captured (when last)
										// and removed
		} while (!struct.isClean());

		nextTokenStart = end;

		return input.substring(start, end); // TODO
	}

	public static ImproovedSplitter createSimple(String separator, String input) {
		Map<String, String> paired = Collections.emptyMap();

		Set<String> unpaired = new HashSet<>();
		unpaired.add("\"");
		unpaired.add("'");

		return new ImproovedSplitter(separator, input, unpaired, paired);
	}

	public static ImproovedSplitter createExtended(String separator, String input) {
		Map<String, String> paired = new HashMap<>();
		paired.put("(", ")");
		paired.put("[", "]");
		paired.put("{", "}");
		paired.put("<", ">");

		Set<String> unpaired = new HashSet<>();
		unpaired.add("\"");
		unpaired.add("'");
		unpaired.add("`");

		return new ImproovedSplitter(separator, input, unpaired, paired);
	}

}
