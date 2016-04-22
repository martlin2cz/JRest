package cz.martlin.jrest.protocol.serializers.simple;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresps.simple.ResponseStatus;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.protocol.serializers.simple.SimplesBasicLinedSerializer;

public class BasicLinedSerializerTest {
	private final SimplesBasicLinedSerializer serializer = new SimplesBasicLinedSerializer();

	@Test
	public void testSerializeResponse() throws JRestException {
		SimpleResponse response = new SimpleResponse(ResponseStatus.WARN, "Lorem Ipsum", "Data incomplete");
		String expected = "WARN\n" + //
				"Data incomplete\n" + //
				"Lorem Ipsum";
		assertEquals(expected, serializer.serializeResponse(response));
	}

	@Test
	public void testDeserializeResponse() throws JRestException {
		String response = "OK\n" + //
				"today\n" + //
				"foo bar baz";
		SimpleResponse expected = new SimpleResponse(ResponseStatus.OK, "foo bar baz", "today");
		assertEquals(expected, serializer.deserializeResponse(response));
	}

	@Test
	public void testSerializeRequest() throws JRestException {
		SimpleRequest request = new SimpleRequest("gimme", "lorem ipsum");
		String expected = "gimme\n" + //
				"lorem ipsum";
		assertEquals(expected, serializer.serializeRequest(request));
	}

	@Test
	public void testDeserializeRequest() throws JRestException {
		String request = "get-newest\n" + //
				"foobar.txt\n" + //
				"content of file";
		SimpleRequest expected = new SimpleRequest("get-newest", "foobar.txt", "content of file");
		assertEquals(expected, serializer.deserializeRequest(request));
	}

}
