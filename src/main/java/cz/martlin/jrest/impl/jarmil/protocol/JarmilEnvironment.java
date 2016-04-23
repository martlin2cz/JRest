package cz.martlin.jrest.impl.jarmil.protocol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class JarmilEnvironment {
	private final Map<String, Object> objects;
	private final Set<Class<?>> classes;

	public JarmilEnvironment() {
		super();
		this.objects = new HashMap<>();
		this.classes = new HashSet<>();
	}

	public JarmilEnvironment(Map<String, Object> objects) {
		super();
		this.objects = objects;
		this.classes = new HashSet<>();
	}

	public JarmilEnvironment(Set<Class<?>> classes) {
		super();
		this.objects = new HashMap<>();
		this.classes = classes;
	}

	public JarmilEnvironment(Map<String, Object> objects, Set<Class<?>> classes) {
		super();
		this.objects = objects;
		this.classes = classes;
	}

	public void addObject(String name, Object object) {
		objects.put(name, object);
	}

	public void addClass(Class<?> clazz) {
		classes.add(clazz);
	}

	public void removeObject(String name) {
		objects.remove(name);
	}

	public void removeClass(Class<?> clazz) {
		classes.remove(clazz);
	}

	public Object findObject(String name) {
		return objects.get(name);
	}

	public boolean findClass(Class<?> clazz) {
		return classes.contains(clazz);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		result = prime * result + ((objects == null) ? 0 : objects.hashCode());
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
		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		if (objects == null) {
			if (other.objects != null)
				return false;
		} else if (!objects.equals(other.objects))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JarmilEnvironment [objects=" + objects + ", classes=" + classes + "]";
	}

}