package cz.martlin.jrest.protocol.encoders;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jrest.protocol.misc.TokenInfo;

public class NoopEncoderTest {
	private final NoopEncoder encoder = new NoopEncoder();
	
	
	@Test
	public void testEncode() throws Exception {
		assertEquals("foo", encoder.encode("foo"));
		assertEquals("Lorem Ipsum", encoder.encode("Lorem Ipsum"));
	}

	@Test
	public void testDecode() throws Exception {
		assertEquals("foo", encoder.decode("foo"));
		assertEquals("Lorem Ipsum", encoder.decode("Lorem Ipsum"));
		
	}

	@Test
	public void testFindNext() throws Exception {
		assertEquals(new TokenInfo(0, 3, 4), encoder.findNext("foo bar baz", 0, " "));
		assertEquals(new TokenInfo(4, 7, 8), encoder.findNext("foo bar baz", 4, " "));
		assertEquals(new TokenInfo(8, 11, 11), encoder.findNext("foo bar baz", 8, " "));
		
		
		assertEquals(new TokenInfo(0, 3, 6), encoder.findNext("foo \t bar \t baz", 0, "[ \t]+"));
	}

}
