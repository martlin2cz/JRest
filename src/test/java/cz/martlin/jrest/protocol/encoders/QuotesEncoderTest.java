package cz.martlin.jrest.protocol.encoders;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jrest.misc.strings.TokenInfo;

public class QuotesEncoderTest {
	private final QuotesEncoder encoder = new QuotesEncoder();

	@Test
	public void testEncode() {
		assertEquals("\"foo\"", encoder.encode("foo"));
		assertEquals("\"foo\\\"bar\"", encoder.encode("foo\"bar"));
		assertEquals("\"foo\\\"bar\\\"baz\"", encoder.encode("foo\"bar\"baz"));
		assertEquals("\"foo\\\\\"bar\"", encoder.encode("foo\\\"bar"));
	}

	@Test
	public void testDecode() {
		assertEquals("foo", encoder.decode("\"foo\""));
		assertEquals("foo\"bar", encoder.decode("\"foo\\\"bar\""));
		assertEquals("foo\"bar\"baz", encoder.decode("\"foo\\\"bar\\\"baz\""));
		assertEquals("foo\\\"bar", encoder.decode("\"foo\\\\\"bar\""));
	}

	@Test
	public void testBi() {
		String input1 = "foo\"bar\"baz";
		assertEquals(input1, encoder.decode(encoder.encode(input1)));
	}
	
	@Test
	public void testFindNext() {
		assertEquals(new TokenInfo(0, 5, 6), encoder.findNext("\"foo\" \"bar\"", 0, " "));
		assertEquals(new TokenInfo(6, 11, 11), encoder.findNext("\"foo\" \"bar\"", 6, " "));
		assertEquals(new TokenInfo(0, 9, 10), encoder.findNext("\"foo bar\" \"aux\"", 0, " "));
	}

}
