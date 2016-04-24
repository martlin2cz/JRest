package cz.martlin.jrest.impl.jarmil.reqresp;

/**
 * Response status of Jarmil.
 * 
 * @author martin
 *
 */
public enum JarmilResponseStatus {
	/**
	 * Everything is OK.
	 */
	OK,

	/**
	 * Cannot find target (class/object) or method.
	 */
	UNKNOWN_TARGET,

	/**
	 * Error during the invocation.
	 */
	INVOCATION_FAILED
}
