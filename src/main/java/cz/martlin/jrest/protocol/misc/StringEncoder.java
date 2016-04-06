package cz.martlin.jrest.protocol.misc;

/**
 * Encodes strings before transmit (and decodes correctly after transmit) such
 * that can be simply joined into one string and nextly splitted. Examples of
 * encoded strings:
 * 
 * <pre>
 * "foo bar" "baz qux"
 * [[foo bar]] [[baz qux]]
 * &lt;tok&gt;foo bar&lt;/tok&gt; &lt;tok&gt;baz qux&lt;/tok&gt;
 * </pre>
 * 
 * @author martin
 *
 */
public interface StringEncoder {

	/**
	 * Encodes given string.
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract String encode(String input) throws Exception;

	/**
	 * Decodes given string.
	 * 
	 * @param input
	 * @return
	 * @throws Exception
	 */
	public abstract String decode(String input) throws Exception;

	/**
	 * Finds next "token" (encoded string) followed by separator (match of given
	 * separator regex).
	 * 
	 * @param input
	 * @param from
	 * @param separator
	 * @return
	 * @throws Exception
	 */
	public abstract TokenInfo findNext(String input, int from, String separator) throws Exception;
}
