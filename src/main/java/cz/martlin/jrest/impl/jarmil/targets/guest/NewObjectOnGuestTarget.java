package cz.martlin.jrest.impl.jarmil.targets.guest;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.WithClassNameOnGuestTarget;

/**
 * Target on guest side representing the {@link TargetType#NEW}.
 * 
 * @author martin
 *
 */
public class NewObjectOnGuestTarget extends WithClassNameOnGuestTarget implements TargetOnGuest {

	private static final long serialVersionUID = 1364200547793692512L;

	/**
	 * Use factory methods where possible.
	 * 
	 * @param className
	 */
	protected NewObjectOnGuestTarget(String className) {
		super(className);
	}

	@Override
	public TargetType getType() {
		return TargetType.NEW;
	}

	@Override
	public String toString() {
		return "NewObjectOnGuestTarget [className=" + getClassName() + "]";
	}

	/**
	 * Creates new target specifier instance of given class name.
	 * 
	 * @param className
	 * @return
	 */
	public static NewObjectOnGuestTarget create(String className) {
		return new NewObjectOnGuestTarget(className);
	}

	/**
	 * Creates new target specifier instance of given class.
	 * 
	 * @param clazz
	 * @return
	 */
	public static NewObjectOnGuestTarget create(Class<? extends JarmilTarget> clazz) {
		String className = clazz.getName();
		return new NewObjectOnGuestTarget(className);
	}

}
