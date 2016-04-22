package cz.martlin.jrest.impl.simple.serializer;

import cz.martlin.jrest.protocol.encoders.NoopEncoder;
import cz.martlin.jrest.protocol.misc.StringEncoder;

/**
 * Represents the simplest serializer. All data items are written with no
 * specific encoding (in fact by {@link NoopEncoder} {@link #ENCODER}), each
 * item on each line (in fact separated by {@link #SEPARATOR}).
 * 
 * <pre>
 * Request format: 
 * [command]{@value #SEPARATOR}[arg1]{@value #SEPARATOR}[arg2]{@value #SEPARATOR}...
 * 
 * Response format: 
 * [status]{@value #SEPARATOR}[meta]{@value #SEPARATOR}[data]
 * </pre>
 * 
 * <em>Warning: The serialized data <strong>must not</strong> contain the {@link #SEPARATOR} value. In such case the data wouldn't be correctly deserialized!</em>
 * 
 * @author martin
 */
public class SimplesBasicLinedSerializer extends SimplesSeparatedSerializer {

	private static final StringEncoder ENCODER = new NoopEncoder();
	private static final String SEPARATOR = "\n";

	public SimplesBasicLinedSerializer() {
		super(ENCODER, SEPARATOR);
	}

}
