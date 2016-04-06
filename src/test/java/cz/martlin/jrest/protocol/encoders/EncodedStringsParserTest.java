package cz.martlin.jrest.protocol.encoders;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.misc.EncodedStringsParser;
import cz.martlin.jrest.protocol.misc.StringEncoder;

public class EncodedStringsParserTest {

	@Test
	public void testWithSimpleSpaceSep() throws JRestException {
		String separator = " ";
		StringEncoder encoder = new NoopEncoder();
		EncodedStringsParser parser = new EncodedStringsParser(encoder, separator);

		List<String> result = parser.parse("foo bar baz");
		assertEquals(3, result.size());
		
		assertEquals("foo", result.get(0));
		assertEquals("bar", result.get(1));
		assertEquals("baz", result.get(2));
	}
	
	@Test
	public void testWithWhitespacesSep() throws JRestException {
		String separator = "[ \t]+";
		StringEncoder encoder = new NoopEncoder();
		EncodedStringsParser parser = new EncodedStringsParser(encoder, separator);

		List<String> result = parser.parse("Lorem \t ipsum    dolor sit");
		assertEquals(4, result.size());
		
		assertEquals("Lorem", result.get(0));
		assertEquals("ipsum", result.get(1));
		assertEquals("dolor", result.get(2));
		assertEquals("sit", result.get(3));
	}

}
