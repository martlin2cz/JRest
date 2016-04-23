package cz.martlin.jrest.impl.jarmil.targets.guest;

import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetType;

public class ObjectOnGuestTarget implements TargetOnGuest {
	private static final long serialVersionUID = -6931026440028792029L;

	private final String name;

	protected ObjectOnGuestTarget(String name) {
		this.name = name;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ObjectOnGuestTarget other = (ObjectOnGuestTarget) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ObjectOnGuestTarget [name=" + name + "]";
	}
	
	public static ObjectOnGuestTarget create(String name) {
		return new ObjectOnGuestTarget(name);
	}

}
