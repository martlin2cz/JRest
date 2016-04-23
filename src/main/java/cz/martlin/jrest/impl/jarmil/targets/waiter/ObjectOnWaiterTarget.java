package cz.martlin.jrest.impl.jarmil.targets.waiter;

import java.lang.reflect.Method;
import java.util.List;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.target.TargetType;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;

public class ObjectOnWaiterTarget implements TargetOnWaiter {
	private static final long serialVersionUID = 835350267856384312L;

	private final String name;
	private final JarmilTarget targetObject;

	protected ObjectOnWaiterTarget(String name, JarmilTarget targetObject) {
		super();
		this.name = name;
		this.targetObject = targetObject;
	}

	@Override
	public String getIdentifier() {
		return getName();
	}

	@Override
	public TargetType getType() {
		return TargetType.OBJECT;
	}

	public String getName() {
		return name;
	}

	public JarmilTarget getTargetObject() {
		return targetObject;
	}

	@Override
	public TargetOnGuest toOnGuestTarget() {
		return ObjectOnGuestTarget.create(name);
	}

	@Override
	public Object invoke(Method method, List<Object> parameters) throws Exception {
		Object[] params = parameters.toArray();
		return method.invoke(targetObject, params);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((targetObject == null) ? 0 : targetObject.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ObjectOnWaiterTarget other = (ObjectOnWaiterTarget) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (targetObject == null) {
			if (other.targetObject != null)
				return false;
		} else if (!targetObject.equals(other.targetObject))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObjectOnWaiterTarget [name=" + name + ", targetObject=" + targetObject + "]";
	}

	/**
	 * 
	 * @param name
	 * @param targetObject
	 * @return
	 */
	public static ObjectOnWaiterTarget create(String name, JarmilTarget targetObject) {
		return new ObjectOnWaiterTarget(name, targetObject);
	}
}
