package cz.martlin.jrest.impl.jarmil.targets.waiter;

import java.lang.reflect.Method;
import java.util.List;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.WithClassOnWaiterTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.StaticClassOnGuestTarget;

/**
 * Represents the target specifier corresponding to {@link TargetType#STATIC}.
 * 
 * @author martin
 *
 */
public class StaticClassOnWaiterTarget extends WithClassOnWaiterTarget implements TargetOnWaiter {

	private static final long serialVersionUID = 8753927779853075158L;

	/**
	 * Use factory methods where possible.
	 * 
	 * @param clazz
	 */
	protected StaticClassOnWaiterTarget(Class<? extends JarmilTarget> clazz) {
		super(clazz);
	}

	@Override
	public TargetType getType() {
		return TargetType.STATIC;
	}

	@Override
	public TargetOnGuest toOnGuestTarget() {
		Class<? extends JarmilTarget> clazz = getClazz();
		return StaticClassOnGuestTarget.create(clazz);
	}

	@Override
	public Object invoke(Method method, List<Object> parameters) throws Exception {
		Object[] params = parameters.toArray();
		return method.invoke(null, params);
	}

	@Override
	public String toString() {
		return "StaticClassOnWaiterTarget [clazz=" + getClazz() + "]";
	}

	/**
	 * Creates instance of given class.
	 * 
	 * @param clazz
	 * @return
	 */
	public static StaticClassOnWaiterTarget create(Class<? extends JarmilTarget> clazz) {
		return new StaticClassOnWaiterTarget(clazz);
	}
}
