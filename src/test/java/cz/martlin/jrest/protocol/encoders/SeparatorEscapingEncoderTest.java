package cz.martlin.jrest.protocol.encoders;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jrest.misc.strings.TokenInfo;

public class SeparatorEscapingEncoderTest {
	private static final String SEP_SPACE = " ";
	private static final String SEP_3EXMS = "!!!";
	private static final String SEP_SWSS_RE = "[ \t]+";
	private static final String SEP_SWSS_TEXT = "  ";

	private static final String ESC = SeparatorEscapingEncoder.ESCAPE;
	private final SeparatorEscapingEncoder encWspace = new SeparatorEscapingEncoder(SEP_SPACE);
	private final SeparatorEscapingEncoder encW3exms = new SeparatorEscapingEncoder(SEP_3EXMS);
	private final SeparatorEscapingEncoder encWswss = new SeparatorEscapingEncoder(SEP_SWSS_RE);

	// FIXME
	@Test
	public void testMatchesSeparator() {

		assertTrue(encWspace.matchesSeparator(SEP_SPACE));
		assertTrue(encW3exms.matchesSeparator(SEP_3EXMS));
		assertTrue(encWswss.matchesSeparator(SEP_SWSS_TEXT));

		assertFalse(encWspace.matchesSeparator(ESC + SEP_SPACE));
		assertFalse(encW3exms.matchesSeparator(ESC + SEP_3EXMS));
		assertFalse(encWswss.matchesSeparator(ESC + SEP_SWSS_TEXT));

		assertTrue(encWspace.matchesSeparator(ESC + ESC + SEP_SPACE));
		assertTrue(encW3exms.matchesSeparator(ESC + ESC + SEP_3EXMS));
		assertTrue(encWswss.matchesSeparator(ESC + ESC + SEP_SWSS_TEXT));

	}

	// FIXME
	@Test
	public void testMatchesNotSeparator() {

		assertFalse(encWspace.matchesNotSeparator(SEP_SPACE));
		assertFalse(encW3exms.matchesNotSeparator(SEP_3EXMS));
		assertFalse(encWswss.matchesNotSeparator(SEP_SWSS_TEXT));

		assertTrue(encWspace.matchesNotSeparator(ESC + SEP_SPACE));
		assertTrue(encW3exms.matchesNotSeparator(ESC + SEP_3EXMS));
		assertTrue(encWswss.matchesNotSeparator(ESC + SEP_SWSS_TEXT));

		assertFalse(encWspace.matchesNotSeparator(ESC + ESC + SEP_SPACE));
		assertFalse(encW3exms.matchesNotSeparator(ESC + ESC + SEP_3EXMS));
		assertFalse(encWswss.matchesNotSeparator(ESC + ESC + SEP_SWSS_TEXT));

	}

	@Test
	public void testEncode() {
		assertEquals("foo", encWspace.encode("foo"));
		assertEquals("fooE bar", encWspace.encode("foo bar"));
		assertEquals("fooE E barE baz", encWspace.encode("foo  bar baz"));
		assertEquals("fooEE bar", encWspace.encode("fooE bar"));
		assertEquals("E ", encWspace.encode(" "));

		assertEquals("foo bar", encW3exms.encode("foo bar"));
		assertEquals("fooE!!!bar", encW3exms.encode("foo!!!bar"));
		assertEquals("fooE!!!!bar", encW3exms.encode("foo!!!!bar"));
		assertEquals("E!!!", encW3exms.encode("!!!"));

		assertEquals("foo", encWswss.encode("foo"));
		assertEquals("fooE bar", encWswss.encode("foo bar"));
		assertEquals("fooE  barE baz", encWswss.encode("foo  bar baz"));
		assertEquals("fooE	barE   baz", encWswss.encode("foo	bar   baz"));
		assertEquals("fooEE bar", encWswss.encode("fooE bar"));
		assertEquals("E ", encWswss.encode(" "));
	}

	@Test
	public void testDecode() {
		assertEquals("foo", encWspace.decode("foo"));

		assertEquals("foo bar", encWspace.decode("fooE bar"));
		assertEquals("foo  bar baz", encWspace.decode("fooE E barE baz"));
		assertEquals("fooE bar", encWspace.decode("fooEE bar"));

		assertEquals(" ", encWspace.decode("E "));

		assertEquals("foo bar", encW3exms.decode("foo bar"));
		assertEquals("foo!!!bar", encW3exms.decode("fooE!!!bar"));
		assertEquals("foo!!!!bar", encW3exms.decode("fooE!!!!bar"));
		assertEquals("!!!", encW3exms.decode("E!!!"));

		assertEquals("foo", encWswss.decode("foo"));
		assertEquals("foo bar", encWswss.decode("fooE bar"));
		assertEquals("foo  bar baz", encWswss.decode("fooE  barE baz"));
		assertEquals("foo	bar   baz", encWswss.decode("fooE	barE   baz"));
		assertEquals("fooE bar", encWswss.decode("fooEE bar"));
		assertEquals(" ", encWswss.decode("E "));
	}

	@Test
	public void testFindNext() {
		assertEquals(new TokenInfo(0, 3, 4), encWspace.findNext("foo bar", 0, SEP_SPACE));
		assertEquals(new TokenInfo(4, 7, 7), encWspace.findNext("foo bar", 4, SEP_SPACE));
		// FIXME assertEquals(new TokenInfo(0, 8, 9), encWspace.findNext("fooE
		// bar baz", 0, SEP_SPACE));
		assertEquals(new TokenInfo(0, 2, 3), encWspace.findNext("E  baz", 0, SEP_SPACE));

		assertEquals(new TokenInfo(0, 7, 7), encW3exms.findNext("foo bar", 0, SEP_3EXMS));
		assertEquals(new TokenInfo(0, 3, 6), encW3exms.findNext("foo!!!bar", 0, SEP_3EXMS));
		assertEquals(new TokenInfo(6, 9, 12), encW3exms.findNext("foo!!!bar!!!baz", 6, SEP_3EXMS));
		// FIXME assertEquals(new TokenInfo(0, 10, 13),
		// encW3exms.findNext("fooE!!!bar!!!baz", 0, SEP_3EXMS));
		assertEquals(new TokenInfo(0, 2, 5), encW3exms.findNext("E!!!!!!baz", 0, SEP_3EXMS));

		assertEquals(new TokenInfo(0, 3, 4), encWswss.findNext("foo bar", 0, SEP_SWSS_RE));
		assertEquals(new TokenInfo(0, 3, 5), encWswss.findNext("foo  bar", 0, SEP_SWSS_RE));
		// FIXME assertEquals(new TokenInfo(0, 9, 10), encWswss.findNext("fooE
		// bar baz", 0, SEP_SWSS));

	}

}
