package cz.martlin.jrest.misc.strings;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jrest.misc.strings.SimpleTokensParser;

public class TokensIteratorTest {

	// TODO
	// FIXME
	// @Test
	// public void testBasic() {
	// SimpleTokensParser iter = new SimpleTokensParser("A B C", " ");
	//
	// assertEquals(new TokenInfo(0, 1, 2), iter.findNext(0));
	// assertEquals(new TokenInfo(2, 3, 4), iter.findNext(2));
	// assertEquals(new TokenInfo(4, 5, 5), iter.findNext(4));
	//
	// assertEquals(new StringToken(0, "A", " "), iter.next());
	// assertEquals(new StringToken(2, "B", " "), iter.next());
	// assertEquals(new StringToken(4, "C", ""), iter.next());
	//
	// assertFalse(iter.hasNext());
	// }
	//
	// @Test
	// public void testVariousTokens() {
	// SimpleTokensParser iter = new SimpleTokensParser("foo||bar|| baz
	// ||quuux", "\\|\\|");
	//
	// assertEquals(new StringToken(0, "foo", "||"), iter.next());
	// assertEquals(new StringToken(5, "bar", "||"), iter.next());
	// assertEquals(new StringToken(10, " baz ", "||"), iter.next());
	// assertEquals(new StringToken(17, "quuux", ""), iter.next());
	//
	// assertFalse(iter.hasNext());
	// }
	//
	// @Test
	// public void testRegexSeparator() {
	// SimpleTokensParser iter = new SimpleTokensParser("Lorem Ipsum \n \t Dolor
	// _ Sit \t Amet. Cons...",
	// "[ \t\n\\.\\,_]+");
	//
	// assertEquals(new StringToken(0, "Lorem", " "), iter.next());
	// assertEquals(new StringToken(6, "Ipsum", " \n \t "), iter.next());
	// assertEquals(new StringToken(16, "Dolor", " _ "), iter.next());
	// assertEquals(new StringToken(24, "Sit", " \t "), iter.next());
	// assertEquals(new StringToken(30, "Amet", ". "), iter.next());
	// assertEquals(new StringToken(36, "Cons", "..."), iter.next());
	//
	// assertFalse(iter.hasNext());
	// }
}
