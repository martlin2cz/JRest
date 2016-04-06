package cz.martlin.jrest.protocol.serializers;

import static org.junit.Assert.*;

import org.junit.Test;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;

public class BasicLinedSerializerTest {
	private final BasicLinedSerializer serializer = new BasicLinedSerializer();

	@Test
	public void testSerializeResponse() throws JRestException {
		JRestResponse response = new JRestResponse(ResponseStatus.WARN, "Lorem Ipsum", "Data incomplete");
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
		JRestResponse expected = new JRestResponse(ResponseStatus.OK, "foo bar baz", "today");
		assertEquals(expected, serializer.deserializeResponse(response));
	}

	@Test
	public void testSerializeRequest() throws JRestException {
		JRestRequest request = new JRestRequest("gimme", "lorem ipsum");
		String expected = "gimme\n" + //
				"lorem ipsum";
		assertEquals(expected, serializer.serializeRequest(request));
	}

	@Test
	public void testDeserializeRequest() throws JRestException {
		String request = "get-newest\n" + //
				"foobar.txt\n" + //
				"content of file";
		JRestRequest expected = new JRestRequest("get-newest", "foobar.txt", "content of file");
		assertEquals(expected, serializer.deserializeRequest(request));
	}

}
