package cz.martlin.jrest.impl.jarmil.targets.guest;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.WithClassNameOnGuestTarget;

public class StaticClassOnGuestTarget extends WithClassNameOnGuestTarget implements TargetOnGuest {

	private static final long serialVersionUID = 9049495076849442328L;

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
	 * 
	 * @param className
	 * @return
	 */
	public static StaticClassOnGuestTarget create(String className) {
		return new StaticClassOnGuestTarget(className);
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static StaticClassOnGuestTarget create(Class<? extends JarmilTarget> clazz) {
		String className = clazz.getName();
		return new StaticClassOnGuestTarget(className);
	}

}
