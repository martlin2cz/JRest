package cz.martlin.jrest.impl.jarmil.targets;

import cz.martlin.jrest.impl.jarmil.target.TargetOfInvocation;
import cz.martlin.jrest.impl.jarmil.target.TargetType;

public abstract class WithClassNameOnGuestTarget implements TargetOfInvocation {

	private static final long serialVersionUID = -1801657162109285704L;
	private final String className;

	public WithClassNameOnGuestTarget(String className) {
		this.className = className;

	}

	public String getClassName() {
		return className;
	}

	@Override
	public String getIdentifier() {
		return getClassName();
	}

	@Override
	public abstract TargetType getType();

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((className == null) ? 0 : className.hashCode());
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
		WithClassNameOnGuestTarget other = (WithClassNameOnGuestTarget) obj;
		if (className == null) {
			if (other.className != null)
				return false;
		} else if (!className.equals(other.className))
			return false;
		return true;
	}

	@Override
	public abstract String toString();
}
