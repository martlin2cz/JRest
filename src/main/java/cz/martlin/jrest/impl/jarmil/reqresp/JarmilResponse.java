package cz.martlin.jrest.impl.jarmil.reqresp;

import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractResponse;

/**
 * Represents response in Jarmil implementation. The response compouds of
 * status, data and type of data. Note that data can contain thrown exception
 * (if status is not {@link JarmilResponseStatus#OK}).
 * 
 * @author martin
 *
 */
public class JarmilResponse implements JRestAbstractResponse {

	private final JarmilResponseStatus status;
	private final Object data;
	private final Class<?> type;

	/**
	 * If possible use static fatory methods.
	 * 
	 * @param status
	 * @param data
	 * @param type
	 */
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

	public static JarmilResponse createNonnullOk(Object value) {
		return new JarmilResponse(JarmilResponseStatus.OK, value, value.getClass());
	}

	public static JarmilResponse createNullOk(Class<?> type) {
		return new JarmilResponse(JarmilResponseStatus.OK, null, type);
	}

	public static JarmilResponse createUnknownTarget(TargetOnGuest targetOnGuest, String method, Exception e) {
		String message = "Target specified by identifier " + targetOnGuest.getIdentifier() + "(of type "
				+ targetOnGuest.getType() + ") or it's method " + method + " not found. Caused by: " + e;

		return new JarmilResponse(JarmilResponseStatus.UNKNOWN_TARGET, message, String.class);
	}

	public static JarmilResponse createIvocationError(Exception e) {
		return new JarmilResponse(JarmilResponseStatus.INVOCATION_FAILED, e, e.getClass());
	}

}
