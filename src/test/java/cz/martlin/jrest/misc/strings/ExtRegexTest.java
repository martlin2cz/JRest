package cz.martlin.jrest.misc.strings;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExtRegexTest {
	private final ExtRegex spaceRegex = new ExtRegex(" ");
	private final ExtRegex digitRegex = new ExtRegex("([0-9])");
	private final ExtRegex whitesRegex = new ExtRegex("([ \t]+)");
	private final ExtRegex abcWdotRegex = new ExtRegex("(.|^)(ABC)");

	@Test
	public void testFindFirst() {
		assertEquals(new RegexMatchInfo(3, 4), spaceRegex.findFirst(0, "foo bar baz"));
		assertEquals(new RegexMatchInfo(3, 4), spaceRegex.findFirst(3, "foo bar baz"));
		assertEquals(new RegexMatchInfo(7, 8), spaceRegex.findFirst(4, "foo bar baz"));
		assertEquals(null, spaceRegex.findFirst(0, "foobarbaz"));

		assertEquals(new RegexMatchInfo(0, 1), digitRegex.findFirst(0, "10"));
		assertEquals(new RegexMatchInfo(1, 2), digitRegex.findFirst(1, "10"));

		assertEquals(new RegexMatchInfo(5, 6), whitesRegex.findFirst(0, "Lorem Ipsum"));
		assertEquals(new RegexMatchInfo(5, 8), whitesRegex.findFirst(0, "Lorem   Ipsum"));
		assertEquals(new RegexMatchInfo(5, 8), whitesRegex.findFirst(0, "Lorem \t Ipsum \t ..."));
		assertEquals(new RegexMatchInfo(13, 16), whitesRegex.findFirst(8, "Lorem \t Ipsum \t ..."));
		assertEquals(null, whitesRegex.findFirst(16, "Lorem \t Ipsum \t ..."));
	}

	@Test
	public void testReplaceAll() {
		assertEquals("foo_bar_baz", spaceRegex.replaceAll(0, "foo bar baz", "_"));
		assertEquals("foo bar_baz", spaceRegex.replaceAll(4, "foo bar baz", "_"));
		assertEquals("foo[ ]bar[ ]baz", spaceRegex.replaceAll(0, "foo bar baz", "[ ]"));
		assertEquals("(SPACE)(SPACE)", spaceRegex.replaceAll(0, "  ", "(SPACE)"));
		
		assertEquals("[1][2][3][4]", digitRegex.replaceAll(0, "1234", "[$1]"));
		
		assertEquals("foo;bar;baz", whitesRegex.replaceAll(0, "foo  bar \t baz", ";"));
		assertEquals("foo  bar;baz", whitesRegex.replaceAll(5, "foo  bar \t baz", ";"));
		assertEquals("foo[  ]bar[ \t ]baz", whitesRegex.replaceAll(0, "foo  bar \t baz", "[$1]"));
		
		assertEquals("foo[  ][ ]bar[ \t\t][\t]baz", whitesRegex.replaceAll(0, "foo  bar \t\tbaz", "[$1]", 1));
		assertEquals("!!XXX", whitesRegex.replaceAll(0, "\t\tXXX", "!", 1));
		assertEquals("!!XXX!!XXX", whitesRegex.replaceAll(0, "\t\tXXX\t\tXXX", "!", 1));
		
		assertEquals("[x]abc", abcWdotRegex.replaceAll(0, "xABC", "[$1]abc"));
		assertEquals("[]abc", abcWdotRegex.replaceAll(0, "ABC", "[$1]abc"));
		assertEquals("[]abc[x]abc", abcWdotRegex.replaceAll(0, "ABCxABC", "[$1]abc"));
		assertEquals("[]abc[C]abcC", abcWdotRegex.replaceAll(0, "ABCABC", "[$1]abc", 1));
		
	}

}
