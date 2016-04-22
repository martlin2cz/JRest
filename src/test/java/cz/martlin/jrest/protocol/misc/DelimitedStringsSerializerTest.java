package cz.martlin.jrest.protocol.misc;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DelimitedStringsSerializerTest {
	private final DelimitedStringsSerializer serializer = new DelimitedStringsSerializer();

	@Test
	public void testSerialize() throws IOException {
		List<String> record = Arrays.asList(new String[] { //
				"foo", "bar", "baz" });

		String expected = "foo bar baz\n";
		assertEquals(expected, serializer.serialize(record));
	}

	@Test
	public void testDeserialize() throws IOException {
		String csv = "foo bar baz\n";

		List<String> expected = Arrays.asList(new String[] { //
				"foo", "bar", "baz" });
		assertEquals(expected, serializer.deserialize(csv));
	}

}
