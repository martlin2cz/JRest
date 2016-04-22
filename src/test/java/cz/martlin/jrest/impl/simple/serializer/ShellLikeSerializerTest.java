package cz.martlin.jrest.impl.simple.serializer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jrest.impl.simple.reqresps.ResponseStatus;
import cz.martlin.jrest.impl.simple.reqresps.SimpleRequest;
import cz.martlin.jrest.impl.simple.reqresps.SimpleResponse;
import cz.martlin.jrest.impl.simple.serializer.SimplesShellLikeSerializer;
import cz.martlin.jrest.misc.JRestException;

public class ShellLikeSerializerTest {
	private final SimplesShellLikeSerializer serializer;

	public ShellLikeSerializerTest() {
		serializer = new SimplesShellLikeSerializer();
	}

	@Test
	public void testSerializeRequest() throws JRestException {
		SimpleRequest request = new SimpleRequest("gimme", "lorem ipsum");
		String expected = "gimme \"lorem ipsum\"\n";

		assertEquals(expected, serializer.serializeRequest(request));
	}

	@Test
	public void testDeserializeRequest() throws JRestException {
		String request = "get-newest foobar.txt \"content of file\"\n";
		SimpleRequest expected = new SimpleRequest("get-newest", "foobar.txt", "content of file");

		assertEquals(expected, serializer.deserializeRequest(request));
	}

	@Test
	public void testSerializeResponse() throws JRestException {
		SimpleResponse response = new SimpleResponse(ResponseStatus.WARN, "Lorem Ipsum", "Data incomplete");
		String expected = "WARN \"Data incomplete\" \"Lorem Ipsum\"\n";

		assertEquals(expected, serializer.serializeResponse(response));
	}

	@Test
	public void testDeserializeResponse() throws JRestException {
		String response = "OK today \"foo bar baz\"\n";
		SimpleResponse expected = new SimpleResponse(ResponseStatus.OK, "foo bar baz", "today");

		assertEquals(expected, serializer.deserializeResponse(response));
	}

}
