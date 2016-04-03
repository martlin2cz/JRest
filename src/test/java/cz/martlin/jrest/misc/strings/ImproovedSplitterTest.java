package cz.martlin.jrest.misc.strings;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.*;

public class ImproovedSplitterTest {

	@Test
	public void testBasic() {
		testWithSimple("A B C", " ", "A", "B", "C");
	}

	private void testWithSimple(String input, String separator, String... tokens) {
		ImproovedSplitter splitter = ImproovedSplitter.createSimple(separator, input);

		List<String> actual = list(splitter);
		List<String> expected = Arrays.asList(tokens);

		assertEquals(expected, actual);
	}

	public static <T> List<T> list(Iterator<T> iterator) {
		List<T> list = new LinkedList<>();

		while (iterator.hasNext()) {
			T item = iterator.next();
			list.add(item);
		}

		return list;
	}

}
