package cz.martlin.jrest.impl.jarmil.targets.guest;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.WithClassNameOnGuestTarget;

/**
 * The static class target specifier on the waiter side (corresponding to
 * {@link TargetType#STATIC}).
 * 
 * @author martin
 *
 */
public class StaticClassOnGuestTarget extends WithClassNameOnGuestTarget implements TargetOnGuest {

	private static final long serialVersionUID = 9049495076849442328L;

	/**
	 * Use factory methods where possible.
	 * 
	 * @param className
	 */
	protected StaticClassOnGuestTarget(String className) {
		super(className);
	}

	@Override
	public TargetType getType() {
		return TargetType.STATIC;
	}

	@Override
	public String toString() {
		return "StaticClassOnGuestTarget [className=" + getClassName() + "]";
	}

	/**
	 * Creates instance of this specifier of given class name.
	 * 
	 * @param className
	 * @return
	 */
	public static StaticClassOnGuestTarget create(String className) {
		return new StaticClassOnGuestTarget(className);
	}

	/**
	 * Creates instance of this specifier of given class.
	 * 
	 * @param clazz
	 * @return
	 */
	public static StaticClassOnGuestTarget create(Class<? extends JarmilTarget> clazz) {
		String className = clazz.getName();
		return new StaticClassOnGuestTarget(className);
	}

}
