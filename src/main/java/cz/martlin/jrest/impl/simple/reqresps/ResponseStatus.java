package cz.martlin.jrest.impl.simple.reqresps;

/**
 * Status of {@link SimpleResponse}.
 * 
 * @author martin
 *
 */
public enum ResponseStatus {
	/**
	 * Processing completed successfully with no problems.
	 */
	OK,

	/**
	 * Processing completed with some small problems, but still less-or-more
	 * successfully.
	 */
	WARN,

	/**
	 * Processing somehow failed.
	 */
	ERROR,

	/**
	 * An fatal error occured during the processing. For instance, internal
	 * error on waiter, or bad command syntax.
	 */
	FATAL
}
