package cz.martlin.jrest.protocol;

import cz.martlin.jrest.misc.strings.TokenInfo;

/**
 * Encodes strings before transmit (and decodes correctly after transmit) such that can be simply joined into one string and nextly splitted. Examples of encoded strings:
 * <pre>
 * "foo bar" "baz qux"
 * [[foo bar]] [[baz qux]]
 * &lt;tok&gt;foo bar&lt;/tok&gt; &lt;tok&gt;baz qux&lt;/tok&gt;
 * </pre>
 * @author martin
 *
 */
public interface StringEncoder {

	public abstract String encode(String input) throws Exception;

	public abstract String decode(String input) throws Exception;
	
	public abstract TokenInfo findNext(String input, int from, String separator) throws Exception;
}
