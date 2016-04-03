package cz.martlin.jrest.misc.strings;

import static org.junit.Assert.assertEquals;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringStructureTest {
	private final Logger log = LoggerFactory.getLogger(getClass());

	@Test
	public void testTrivial() {
		Map<String, String> paired = Collections.emptyMap();
		Set<String> unpaired = Collections.emptySet();

		StringStructure struct = new StringStructure(unpaired, paired, false);

		String input = "foobarbaz";

		run(struct, input, true);

	}

	@Test
	public void testWithSimpleQuote() {
		Map<String, String> paired = Collections.emptyMap();
		Set<String> unpaired = new HashSet<>();
		unpaired.add("'");

		StringStructure struct = new StringStructure(unpaired, paired, false);

		run(struct, "foo", true);
		run(struct, "' bar", false);
		run(struct, "baz", false);
		run(struct, "qux '", true);
		run(struct, "aux", true);
	}

	@Test
	public void testWithSimpleBrackets() {
		Map<String, String> paired = new HashMap<>();
		paired.put("(", ")");

		Set<String> unpaired = Collections.emptySet();

		StringStructure struct = new StringStructure(unpaired, paired, false);

		run(struct, "x + ", true);
		run(struct, " 5 * (", false);
		run(struct, "11 - 4", false);
		run(struct, ") * 6", true);
	}

	@Test
	public void testSomethingMoreComplex() {
		Map<String, String> paired = new HashMap<>();
		paired.put("(", ")");
		paired.put("[", "]");

		Set<String> unpaired = new HashSet<>();
		unpaired.add("'");

		StringStructure struct = new StringStructure(unpaired, paired, false);

		run(struct, "char", true);
		run(struct, "[10", false);
		run(struct, "] foo ", true);
		run(struct, "= (", false);
		run(struct, "char*", false);
		run(struct, ") ", true);
		run(struct, " '", false);
		run(struct, "Lorem Ipsum", false);
		run(struct, "';", true);

	}

	@Test
	public void testWithRecursion() {
		Map<String, String> paired = new HashMap<>();
		paired.put("(", ")");
		paired.put("[", "]");

		Set<String> unpaired = Collections.emptySet();

		StringStructure struct = new StringStructure(unpaired, paired, false);

		run(struct, "42+", true);
		run(struct, "(10*", false);
		run(struct, "(x", false);
		run(struct, "[i", false);
		run(struct, "-1]", false);
		run(struct, "+10)", false);
		run(struct, ")/2", true);
		run(struct, " - sin(", false);
		run(struct, "0.5)", true);

	}

	@Test
	public void testClosingUnmatched() {
		Map<String, String> paired = new HashMap<>();
		paired.put("(", ")");
		paired.put("[", "]");

		Set<String> unpaired = Collections.emptySet();

		StringStructure struct = new StringStructure(unpaired, paired, false);

		run(struct, "(1+", false);
		try {
			run(struct, "2]", true);
			// fail("Should fail"); TODO: throw exception disabled ...
		} catch (IllegalStateException e) {
		}
	}

	private void run(StringStructure struct, String string, boolean cleanAfter) {
		struct.process(string);
		log.debug("After process of {}: {}", string, struct.toString());
		assertEquals(cleanAfter, struct.isClean());
	}

}
