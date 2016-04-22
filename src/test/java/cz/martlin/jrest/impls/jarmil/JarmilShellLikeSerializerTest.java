package cz.martlin.jrest.impls.jarmil;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import cz.martlin.jrest.impl.jarmil.MethodsFinder;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresps.jarmil.JarmilRequest;
import cz.martlin.jrest.protocol.reqresps.jarmil.JarmilResponse;
import cz.martlin.jrest.protocol.serializers.jarmil.JarmilShellLikeSerializer;

public class JarmilShellLikeSerializerTest {

	private static final String LOCALHOST_URL_SERIALIZED = "rO0ABXNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wABGZpbGVxAH4AAUwABGhvc3RxAH4AAUwACHByb3RvY29scQB+AAFMAANyZWZxAH4AAXhw//////////90AAlsb2NhbGhvc3R0AABxAH4AA3QABGh0dHBweA==";
	private final JarmilShellLikeSerializer serializer = createSerializer();

	private static JarmilShellLikeSerializer createSerializer() {
		Map<String, Object> env = new HashMap<>();

		env.put("my-double", 20.57);

		return new JarmilShellLikeSerializer(env);
	}

	@Test
	public void testListToResponseListOfString() {
	}

	@Test
	public void testResponseToListJarmilResponse() {
	}

	@Test
	public void testListToRequestListOfString() {
	}

	@Test
	public void testRequestToListJarmilRequest() {
	}

	@Test
	public void testSerializeRequest() throws JRestException, NoSuchMethodException, IllegalStateException {
		Method method1 = new MethodsFinder().findMethod(Double.class, "toString", Collections.emptyList(), true);
		JarmilRequest req1 = new JarmilRequest(null, "my-double", method1, Collections.emptyList());

		String expected1 = "\" \" my-double toString\n";
		String actual1 = serializer.serializeRequest(req1);

		assertEquals(expected1, actual1);

		JarmilRequest req2 = JarmilRequest.create(Collections.class, "emptyList");

		String expected2 = "java.util.Collections \" \" emptyList\n";
		String actual2 = serializer.serializeRequest(req2);

		assertEquals(expected2, actual2);
	}

	@Test
	public void testDeserializeRequest() throws NoSuchMethodException, IllegalStateException, JRestException {
		Method method1 = new MethodsFinder().findMethod(Double.class, "toString", Collections.emptyList(), true);
		String ser1 = "\" \" my-double toString";

		JarmilRequest expected1 = new JarmilRequest(Double.class, "my-double", method1, Collections.emptyList());
		JarmilRequest actual1 = serializer.deserializeRequest(ser1);

		assertEquals(expected1, actual1);

		String ser2 = "java.util.Collections \" \" emptyList";

		JarmilRequest expected2 = JarmilRequest.create(Collections.class, "emptyList");
		JarmilRequest actual2 = serializer.deserializeRequest(ser2);

		assertEquals(expected2, actual2);
	}

	@Test
	public void testSerializeResponse() throws JRestException, MalformedURLException {
		JarmilResponse response1 = JarmilResponse.createNonullOk(42);

		String expected1 = "OK 42 java.lang.Integer\n";
		String actual1 = serializer.serializeResponse(response1);

		assertEquals(expected1, actual1);

		JarmilResponse response2 = JarmilResponse.createNonullOk(new URL("http://localhost"));

		String expected2 = "OK " + LOCALHOST_URL_SERIALIZED + " java.net.URL\n";
		String actual2 = serializer.serializeResponse(response2);

		assertEquals(expected2, actual2);
	}

	@Test
	public void testDeserializeResponse() throws JRestException, MalformedURLException {
		String ser1 = "OK 42 java.lang.Integer\n";

		JarmilResponse expected1 = JarmilResponse.createNonullOk(42);
		JarmilResponse actual1 = serializer.deserializeResponse(ser1);

		assertEquals(expected1, actual1);

		String ser2 = "OK " + LOCALHOST_URL_SERIALIZED + " java.net.URL\n";

		JarmilResponse expected2 = JarmilResponse.createNonullOk(new URL("http://localhost"));
		JarmilResponse actual2 = serializer.deserializeResponse(ser2);

		assertEquals(expected2, actual2);
	}

}
