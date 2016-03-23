package cz.martlin.jrest.misc;

/**
 * Represents Exception during the JRest process.
 * @author martin
 *
 */
public class JRestException extends Exception {

	private static final long serialVersionUID = -8888111689187705931L;

	public JRestException(String message, Throwable cause) {
		super(message, cause);
	}

	public JRestException(String message) {
		super(message);
	}

	public JRestException(Throwable cause) {
		super(cause);
	}

}
