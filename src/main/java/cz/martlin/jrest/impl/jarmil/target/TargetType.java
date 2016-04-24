package cz.martlin.jrest.impl.jarmil.target;

/**
 * The type of target.
 * 
 * @author martin
 *
 */
public enum TargetType {
	/**
	 * The target is instance of object. The object has a name which is its
	 * identifier.
	 */
	OBJECT,
	/**
	 * The target is class with static method(s).
	 */
	STATIC,
	/**
	 * The target is class with nonparametric constructor, which is instatied on
	 * each invocation.
	 */
	NEW
}
