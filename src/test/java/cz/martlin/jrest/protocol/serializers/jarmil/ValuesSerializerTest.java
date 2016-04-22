package cz.martlin.jrest.protocol.serializers.jarmil;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ValuesSerializerTest {

	private final ValuesSerializer serializer = new ValuesSerializer();

	@Test
	public void testSerialize() {
		assertEquals("42", serializer.serialize(42));
		assertEquals("\"foo\"", serializer.serialize("foo"));
	}

	@Test
	public void testDeserialize() {
		assertEquals(42, serializer.deserialize("42"));
		assertEquals("foo", serializer.deserialize("\"foo\""));
	}

	@Test
	public void testTryInferType() {
		assertEquals(Integer.class, serializer.tryInferType("42"));
		assertEquals(String.class, serializer.tryInferType("\"foo\""));

	}

	// TODO OOOOOOOOOO OOOOO OOO... moooore tests!!!!

}
