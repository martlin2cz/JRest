package cz.martlin.jrest.impl.jarmil.targets;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.target.TargetOfInvocation;
import cz.martlin.jrest.impl.jarmil.target.TargetType;

public abstract class WithClassOnWaiterTarget implements TargetOfInvocation {
	private static final long serialVersionUID = -1357415539511404650L;

	private final Class<? extends JarmilTarget> clazz;

	public WithClassOnWaiterTarget(Class<? extends JarmilTarget> clazz) {
		this.clazz = clazz;
	}

	public Class<? extends JarmilTarget> getClazz() {
		return clazz;
	}

	@Override
	public String getIdentifier() {
		return clazz.getName();
	}

	@Override
	public abstract TargetType getType();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clazz == null) ? 0 : clazz.hashCode());
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
		WithClassOnWaiterTarget other = (WithClassOnWaiterTarget) obj;
		if (clazz == null) {
			if (other.clazz != null)
				return false;
		} else if (!clazz.equals(other.clazz))
			return false;
		return true;
	}

}
