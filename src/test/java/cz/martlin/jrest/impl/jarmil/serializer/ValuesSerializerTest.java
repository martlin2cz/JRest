package cz.martlin.jrest.impl.jarmil.serializer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cz.martlin.jrest.impl.jarmil.serializers.ValuesSerializer;

public class ValuesSerializerTest {

	private final ValuesSerializer serializer = new ValuesSerializer();

	@Test
	public void testSerialize() throws Exception {
		assertEquals("42", serializer.serialize(42));
		assertEquals("\"foo\"", serializer.serialize("foo"));
	}

	@Test
	public void testDeserialize() throws Exception {
		assertEquals(42, serializer.deserialize("42"));
		assertEquals("foo", serializer.deserialize("\"foo\""));
	}

	@Test
	public void testTryInferType() throws Exception {
		assertEquals(Integer.class, serializer.tryInferType("42"));
		assertEquals(String.class, serializer.tryInferType("\"foo\""));

	}

	// TODO OOOOOOOOOO OOOOO OOO... moooore tests!!!!

}
