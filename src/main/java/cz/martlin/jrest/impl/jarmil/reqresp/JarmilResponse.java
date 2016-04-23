package cz.martlin.jrest.impl.jarmil.reqresp;

import cz.martlin.jrest.impl.jarmil.handlers.JarmilResponseStatus;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;

public class JarmilResponse implements JRestAbstractResponse {

	private final JarmilResponseStatus status;
	private final Object data;
	private final Class<?> type;

	public JarmilResponse(JarmilResponseStatus status, Object data, Class<?> type) {
		super();
		this.status = status;
		this.data = data;
		this.type = type;
	}

	public JarmilResponseStatus getStatus() {
		return status;
	}

	public Object getData() {
		return data;
	}

	public Class<?> getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JarmilResponse other = (JarmilResponse) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (status != other.status)
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JarmilResponse [status=" + status + ", data=" + data + ", type=" + type + "]";
	}

	public static JarmilResponse createOk(Object value, Class<?> type) {
		return new JarmilResponse(JarmilResponseStatus.OK, value, type);
	}

	public static JarmilResponse createNonullOk(Object value) {
		return new JarmilResponse(JarmilResponseStatus.OK, value, value.getClass());
	}

	public static JarmilResponse createNullOk(Class<?> type) {
		return new JarmilResponse(JarmilResponseStatus.OK, null, type);
	}

	public static JarmilResponse createUnknownTarget(String name) {
		return new JarmilResponse(JarmilResponseStatus.UNKNOWN_TARGET, name, String.class);
	}

	public static JarmilResponse createUnknownObjectTarget(String name) {
		return createUnknownTarget("Could not found object " + name + " or object not supported");
	}

	public static JarmilResponse createUnknownStaticTarget(Class<?> clazz) {
		return createUnknownTarget("Could not found class " + clazz + " or class no supported");
	}

	public static JarmilResponse createIvocationError(JRestException e) {
		return new JarmilResponse(JarmilResponseStatus.INVOCATION_FAILED, e, e.getClass());
	}


}
