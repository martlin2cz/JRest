package cz.martlin.jrest.impl.jarmil.misc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.target.TargetOnWaiter;
import cz.martlin.jrest.impl.jarmil.target.TargetType;

/**
 * Represents the environment of the Jarmil's JRest. The guest can invoke
 * methods <strong>only</strong> of the targets (classes and objects) given by
 * this environments.
 * 
 * @author martin
 *
 */
public class JarmilEnvironment {
	private final Set<TargetOnWaiter> targets;

	public JarmilEnvironment() {
		super();
		this.targets = new HashSet<>();
	}

	public JarmilEnvironment(TargetOnWaiter... targets) {
		super();
		this.targets = new HashSet<>(Arrays.asList(targets));
	}

	public void add(TargetOnWaiter target) {
		targets.add(target);
	}

	public void remove(TargetOnWaiter target) {
		targets.remove(target.getIdentifier());
	}

	public TargetOnWaiter find(TargetType type, String identifier) {

		for (TargetOnWaiter target : targets) {
			if (target.getType().equals(type) //
					&& target.getIdentifier().equals(identifier)) {
				return target;
			}

		}

		return null;
	}

	public TargetOnWaiter find(TargetOnGuest guest) {
		return find(guest.getType(), guest.getIdentifier());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((targets == null) ? 0 : targets.hashCode());
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
		JarmilEnvironment other = (JarmilEnvironment) obj;
		if (targets == null) {
			if (other.targets != null)
				return false;
		} else if (!targets.equals(other.targets))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JarmilEnvironment [targets=" + targets + "]";
	}

}