package cz.martlin.jrest.impl.jarmil.serializers;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;

import cz.martlin.jrest.impl.jarmil.reqresp.JarmilRequest;
import cz.martlin.jrest.impl.jarmil.reqresp.JarmilResponse;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.StaticClassOnGuestTarget;
import cz.martlin.jrest.misc.JRestException;

public class JarmilShellLikeSerializerTest {

	private static final String LOCALHOST_URL_SERIALIZED = "rO0ABXNyAAxqYXZhLm5ldC5VUkyWJTc2GvzkcgMAB0kACGhhc2hDb2RlSQAEcG9ydEwACWF1dGhvcml0eXQAEkxqYXZhL2xhbmcvU3RyaW5nO0wABGZpbGVxAH4AAUwABGhvc3RxAH4AAUwACHByb3RvY29scQB+AAFMAANyZWZxAH4AAXhw//////////90AAlsb2NhbGhvc3R0AABxAH4AA3QABGh0dHBweA==";
	private final JarmilShellLikeSerializer serializer = new JarmilShellLikeSerializer();

	// @Test public void testListToResponseListOfString() {}
	// @Test public void testResponseToListJarmilResponse() {}
	// @Test public void testListToRequestListOfString() {}
	// @Test public void testRequestToListJarmilRequest() {}

	@Test
	public void testSerializeRequest() throws JRestException, NoSuchMethodException, IllegalStateException {
		TargetOnGuest target1 = ObjectOnGuestTarget.create("my-double");
		JarmilRequest req1 = new JarmilRequest(target1, "compareTo", Arrays.asList((Object) 0.45));

		String expected1 = "object my-double compareTo 0.45d\n";
		String actual1 = serializer.serializeRequest(req1);

		assertEquals(expected1, actual1);

		TargetOnGuest target2 = StaticClassOnGuestTarget.create(Collections.class.getName());

		JarmilRequest req2 = new JarmilRequest(target2, "emptyList", Collections.emptyList());

		String expected2 = "static java.util.Collections emptyList\n";
		String actual2 = serializer.serializeRequest(req2);

		assertEquals(expected2, actual2);
	}

	@Test
	public void testDeserializeRequest() throws NoSuchMethodException, IllegalStateException, JRestException {
		String ser1 = "object my-double compareTo 0.45d";

		TargetOnGuest target1 = ObjectOnGuestTarget.create("my-double");
		JarmilRequest expected1 = new JarmilRequest(target1, "compareTo", Arrays.asList((Object) 0.45));
		JarmilRequest actual1 = serializer.deserializeRequest(ser1);

		assertEquals(expected1, actual1);

		String ser2 = "static java.util.Collections emptyList";

		TargetOnGuest target2 = StaticClassOnGuestTarget.create(Collections.class.getName());
		JarmilRequest expected2 = new JarmilRequest(target2, "emptyList", Collections.emptyList());
		JarmilRequest actual2 = serializer.deserializeRequest(ser2);

		assertEquals(expected2, actual2);
	}

	@Test
	public void testSerializeResponse() throws JRestException, MalformedURLException {
		JarmilResponse response1 = JarmilResponse.createNonnullOk(42);

		String expected1 = "OK 42 java.lang.Integer\n";
		String actual1 = serializer.serializeResponse(response1);

		assertEquals(expected1, actual1);

		JarmilResponse response2 = JarmilResponse.createNonnullOk(new URL("http://localhost"));

		String expected2 = "OK " + LOCALHOST_URL_SERIALIZED + " java.net.URL\n";
		String actual2 = serializer.serializeResponse(response2);

		assertEquals(expected2, actual2);
	}

	@Test
	public void testDeserializeResponse() throws JRestException, MalformedURLException {
		String ser1 = "OK 42 java.lang.Integer\n";

		JarmilResponse expected1 = JarmilResponse.createNonnullOk(42);
		JarmilResponse actual1 = serializer.deserializeResponse(ser1);

		assertEquals(expected1, actual1);

		String ser2 = "OK " + LOCALHOST_URL_SERIALIZED + " java.net.URL\n";

		JarmilResponse expected2 = JarmilResponse.createNonnullOk(new URL("http://localhost"));
		JarmilResponse actual2 = serializer.deserializeResponse(ser2);

		assertEquals(expected2, actual2);
	}

}
