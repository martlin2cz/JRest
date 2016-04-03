package cz.martlin.jrest.protocol;

public class JRestResponse {
	private final ResponseStatus status;
	private final String data;
	private final String meta;

	public JRestResponse(ResponseStatus status, String data, String meta) {
		super();
		this.status = status;
		this.data = data;
		this.meta = meta;
	}

	public ResponseStatus getStatus() {
		return status;
	}

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

}
