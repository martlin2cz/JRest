package cz.martlin.jrest.impl.jarmil.targets.waiter;

import java.lang.reflect.Method;
import java.util.List;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.WithClassOnWaiterTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.NewObjectOnGuestTarget;

public class NewObjectOnWaiterTarget extends WithClassOnWaiterTarget implements TargetOnWaiter {

	private static final long serialVersionUID = 2062698340423903589L;

	protected NewObjectOnWaiterTarget(Class<? extends JarmilTarget> clazz) {
		super(clazz);
	}

	@Override
	public TargetType getType() {
		return TargetType.NEW;
	}

	@Override
	public TargetOnGuest toOnGuestTarget() {
		Class<? extends JarmilTarget> clazz = getClazz();
		return NewObjectOnGuestTarget.create(clazz);
	}

	@Override
	public Object invoke(Method method, List<Object> parameters) throws Exception {
		JarmilTarget target = getClazz().newInstance();

		Object[] params = parameters.toArray();
		return method.invoke(target, params);
	}

	@Override
	public String toString() {
		return "NewObjectWaiterTarget [clazz=" + getClazz() + "]";
	}

	/**
	 * 
	 * @param clazz
	 * @return
	 */
	public static NewObjectOnWaiterTarget create(Class<? extends JarmilTarget> clazz) {
		return new NewObjectOnWaiterTarget(clazz);
	}

}
