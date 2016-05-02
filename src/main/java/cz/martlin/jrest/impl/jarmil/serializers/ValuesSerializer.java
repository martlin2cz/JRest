package cz.martlin.jrest.impl.jarmil.serializers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

/**
 * Very simple serializer of all objects. The primitives are serialized by the
 * default way, others (the objects) are serialized (so, must implement
 * Serializable) and then converted to {@link Base64} string.
 * 
 * @author martin
 *
 */
public class ValuesSerializer {

	private static final String NULL_STR = "null";

	public ValuesSerializer() {
	}

	public String serialize(Object value) throws Exception {
		if (value == null) {
			return NULL_STR;
		}

		if (value instanceof Integer) {
			return Integer.toString((Integer) value);
		}
		if (value instanceof Long) {
			return Long.toString((Long) value) + "l";
		}
		if (value instanceof Short) {
			return Short.toString((Short) value);
		}
		if (value instanceof Byte) {
			return Byte.toString((Byte) value);
		}
		if (value instanceof Float) {
			return Float.toString((Float) value);
		}
		if (value instanceof Double) {
			return Double.toString((Double) value) + "d";
		}
		if (value instanceof Boolean) {
			return Boolean.toString((Boolean) value);
		}
		if (value instanceof Character) {
			return "'" + Character.toString((Character) value) + "'";
		}
		if (value instanceof String) {
			return "\"" + (String) value + "\"";
		}
		if (value instanceof Enum<?>) {
			return ((Enum<?>) value).name();
		}

		try {
			return serializeObject(value);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot serialize object " + value, e);
		}
	}

	public Object deserialize(String value) throws Exception {
		return deserialize(value, tryInferType(value));
	}

	public Object deserialize(String value, Class<?> type) throws Exception {
		if (NULL_STR.equals(value)) {
			return null;
		}

		if (Integer.class.equals(type)) {
			return Integer.parseInt(value);
		}
		if (Long.class.equals(type)) {
			return Long.parseLong(value);
		}
		if (Short.class.equals(type)) {
			return Short.parseShort(value);
		}
		if (Byte.class.equals(type)) {
			return Byte.parseByte(value);
		}
		if (Float.class.equals(type)) {
			return Float.parseFloat(value);
		}
		if (Double.class.equals(type)) {
			return Double.parseDouble(value);
		}
		if (Boolean.class.equals(type)) {
			return Boolean.parseBoolean(value);
		}
		if (Character.class.equals(type)) {
			return value.charAt(1);
		}
		if (String.class.equals(type)) {
			return value.substring(1, value.length() - 1);
		}
		if (type.isEnum()) {
			return deserializeEnum(value, type);
		}
		
		if (Object.class.isAssignableFrom(type)) {
			try {
				return deserializeObject(value);
			} catch (Exception e) {
				throw new IllegalArgumentException("Cannot serialize object " + value, e);
			}
		}

		throw new IllegalArgumentException("Unsupported type: " + type);
	}

	public Class<?> tryInferType(String value) throws Exception {
		if (NULL_STR.equals(value)) {
			return null;
		}

		if (value.endsWith("=")) {
			return Object.class;
		}

		if (value.matches("^\"(.*)\"$")) {
			return String.class;
		}

		if (value.matches("'([^']|\\')'")) {
			return Character.class;
		}

		if (value.matches("^[0-9]+\\.[0-9]*[dD]$")) {
			return Double.class;
		}

		if (value.matches("^[0-9]+\\.[0-9]*$")) {
			return Float.class;
		}

		if (value.matches("^[0-9]+[lL]$")) {
			return Long.class;
		}

		if (value.matches("^[0-9]+$")) {
			return Integer.class;
		}

		if (value.matches("[a-zA-Z0-9_]+")) {
			// everything else (with no spaces)
			return Enum.class;
		}

		throw new IllegalArgumentException("Unspecifieable type of value: " + value);
	}

	private <T extends Enum<T>> Object deserializeEnum(String value, Class<?> type) {
		Object typeAsObj = type;
		@SuppressWarnings("unchecked")
		Class<T> clazz = (Class<T>) typeAsObj;
		return Enum.valueOf(clazz, value);
	}

	private String serializeObject(Object value) throws Exception {
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		try {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(value);
			byte[] bytes = baos.toByteArray();

			return Base64.getEncoder().encodeToString(bytes);
		} catch (IOException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(baos);
			IOUtils.closeQuietly(oos);
		}
	}

	private Object deserializeObject(String value) throws Exception {
		ByteArrayInputStream bais = null;
		ObjectInputStream ois = null;

		try {
			byte[] bytes = Base64.getDecoder().decode(value);
			bais = new ByteArrayInputStream(bytes);
			ois = new ObjectInputStream(bais);
			Object object = ois.readObject();
			return object;
		} catch (IOException | ClassNotFoundException e) {
			throw e;
		} finally {
			IOUtils.closeQuietly(bais);
			IOUtils.closeQuietly(ois);

		}

	}

	public Class<?> deserializeType(String string) throws ClassNotFoundException {
		if ("void".equals(string)) {
			return void.class;
		}
		if ("int".equals(string)) {
			return int.class;
		}
		if ("short".equals(string)) {
			return short.class;
		}
		if ("long".equals(string)) {
			return long.class;
		}
		if ("byte".equals(string)) {
			return byte.class;
		}
		if ("char".equals(string)) {
			return char.class;
		}
		if ("boolean".equals(string)) {
			return boolean.class;
		}
		if ("float".equals(string)) {
			return float.class;
		}
		if ("double".equals(string)) {
			return double.class;
		}

		return Class.forName(string);
	}

}
