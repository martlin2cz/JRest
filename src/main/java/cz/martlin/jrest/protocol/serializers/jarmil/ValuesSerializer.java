package cz.martlin.jrest.protocol.serializers.jarmil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

import org.apache.commons.io.IOUtils;
import org.apache.commons.io.output.ByteArrayOutputStream;

public class ValuesSerializer {

	private static final String NULL_STR = "null";

	public ValuesSerializer() {
		// TODO Auto-generated constructor stub
	}

	public String serialize(Object value) {
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

	public Object deserialize(String value) {
		return deserialize(value, tryInferType(value));
	}

	public Object deserialize(String value, Class<?> type) throws IllegalArgumentException {
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

		try {
			return deserializeObject(value);
		} catch (Exception e) {
			throw new IllegalArgumentException("Cannot serialize object " + value, e);
		}
	}

	public Class<?> tryInferType(String value) {
		if (NULL_STR.equals(value)) {
			return null;
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

		return Object.class;
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

}
