package cz.martlin.jrest.impl.jarmil.target;

import java.io.Serializable;

/**
 * Base Target specifier interface. Represents target in the purest form.
 * 
 * @author martin
 *
 */
public interface TargetOfInvocation extends Serializable {

	/**
	 * Returns identifier of this target (i.e. class or object name).
	 * 
	 * @return
	 */
	public String getIdentifier();

	/**
	 * Returns type of target. The type should be unique for each of the
	 * target's specifier's implementation.
	 * 
	 * @return
	 */
	public TargetType getType();

}
