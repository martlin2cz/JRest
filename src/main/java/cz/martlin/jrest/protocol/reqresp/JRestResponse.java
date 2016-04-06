package cz.martlin.jrest.protocol.reqresp;

/**
 * JRest Response is the answer to {@link JRestRequest}. The response is defined
 * by its status, data part and metadata part. The status specifies if the
 * processing of the request have been successfully completed or not. The data
 * field contains real data sent from the server. The metadata field contains
 * additional informations, like date of data or - the error/warning message.
 * 
 * @author martin
 *
 */
public class JRestResponse {
	private final ResponseStatus status;
	private final String data;
	private final String meta;

	/**
	 * Constructs response with given params. If possible rather use static
	 * factory methods instead.
	 * 
	 * @param status
	 * @param data
	 * @param meta
	 */
	public JRestResponse(ResponseStatus status, String data, String meta) {
		super();
		this.status = status;
		this.data = data;
		this.meta = meta;
	}

	public ResponseStatus getStatus() {
		return status;
	}

	/**
	 * Returns true, if is this response of given status.
	 * 
	 * @param status
	 * @return
	 */
	public boolean is(ResponseStatus status) {
		return this.status.equals(status);
	}

	public String getData() {
		return data;
	}

	public String getMeta() {
		return meta;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + ((meta == null) ? 0 : meta.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		JRestResponse other = (JRestResponse) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (meta == null) {
			if (other.meta != null)
				return false;
		} else if (!meta.equals(other.meta))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JRestResponse [status=" + status + ", data=" + data + ", meta=" + meta + "]";
	}

	/**
	 * Constructs "OK" response with given data and no metadata.
	 * 
	 * @param data
	 * @return
	 */
	public static JRestResponse ok(String data) {
		return new JRestResponse(ResponseStatus.OK, data, "");
	}

	/**
	 * Constructs "WARN" response with given data and metadata (additionalInfo).
	 * 
	 * @param data
	 * @param aditionalInfo
	 * @return
	 */
	public static JRestResponse warn(String data, String aditionalInfo) {
		return new JRestResponse(ResponseStatus.WARN, data, aditionalInfo);
	}

	/**
	 * Constructs "ERROR" response with given data (if some) and given error
	 * message as metadata.
	 * 
	 * @param data
	 * @param error
	 * @return
	 */
	public static JRestResponse error(String data, String error) {
		return new JRestResponse(ResponseStatus.ERROR, data, error);
	}

	/**
	 * Constructs "FATAL" response with no data and given message as metadata.
	 * 
	 * @param message
	 * @return
	 */
	public static JRestResponse fatal(String message) {
		return new JRestResponse(ResponseStatus.FATAL, "", message);
	}

}
