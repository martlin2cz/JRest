package cz.martlin.jrest.misc;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Test;

/**
 * Test of {@link Tools} class.
 * 
 * @author martin
 *
 */
public class ToolsTest {

	@Test
	public void testBothWriteAndRead() throws JRestException {

		testWriteAndRead("foo bar baz qux aux");
		testWriteAndRead("Lorem Ipsum dolor si amet");
		testWriteAndRead("Příliš žluťoučký kůň úpěl dábelské ódy");
		testWriteAndRead("for I in [4 5 6]; do echo \"\\n ${I} \"; done #Something like bash, yea ");

	}

	private void testWriteAndRead(String expected) throws JRestException {
		ByteArrayOutputStream bous = new ByteArrayOutputStream();
		Tools.write(bous, expected);

		byte[] bytes = bous.toByteArray();

		ByteArrayInputStream bins = new ByteArrayInputStream(bytes);
		String actual = Tools.read(bins);

		assertEquals(expected, actual);
	}

}
