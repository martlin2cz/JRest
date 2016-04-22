package cz.martlin.jrest.protocol.serializers.simple;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresps.simple.ResponseStatus;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;

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
