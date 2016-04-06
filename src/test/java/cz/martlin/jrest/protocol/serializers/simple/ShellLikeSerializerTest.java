package cz.martlin.jrest.protocol.serializers;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestRequest;
import cz.martlin.jrest.protocol.reqresp.JRestResponse;
import cz.martlin.jrest.protocol.reqresp.ResponseStatus;

public class ShellLikeSerializerTest {
	private final ShellLikeSerializer serializer;

	public ShellLikeSerializerTest() {
		serializer = new ShellLikeSerializer();
	}

	@Test
	public void testSerializeRequest() throws JRestException {
		JRestRequest request = new JRestRequest("gimme", "lorem ipsum");
		String expected = "gimme \"lorem ipsum\"\n";

		assertEquals(expected, serializer.serializeRequest(request));
	}

	@Test
	public void testDeserializeRequest() throws JRestException {
		String request = "get-newest foobar.txt \"content of file\"\n";
		JRestRequest expected = new JRestRequest("get-newest", "foobar.txt", "content of file");

		assertEquals(expected, serializer.deserializeRequest(request));
	}

	@Test
	public void testSerializeResponse() throws JRestException {
		JRestResponse response = new JRestResponse(ResponseStatus.WARN, "Lorem Ipsum", "Data incomplete");
		String expected = "WARN \"Data incomplete\" \"Lorem Ipsum\"\n";

		assertEquals(expected, serializer.serializeResponse(response));
	}

	@Test
	public void testDeserializeResponse() throws JRestException {
		String response = "OK today \"foo bar baz\"\n";
		JRestResponse expected = new JRestResponse(ResponseStatus.OK, "foo bar baz", "today");

		assertEquals(expected, serializer.deserializeResponse(response));
	}

	@Test
	public void testListToCSVstring() throws IOException {
		List<String> record = Arrays.asList(new String[] { //
				"foo", "bar", "baz" });

		String expected = "foo bar baz\n";
		assertEquals(expected, serializer.listToCSVstring(record));
	}

	@Test
	public void testCsvStringToRecord() throws IOException {
		String csv = "foo bar baz\n";

		List<String> expected = Arrays.asList(new String[] { //
				"foo", "bar", "baz" });
		assertEquals(expected, serializer.csvStringToRecord(csv));

	}

}
