package cz.martlin.jrest.impl.jarmil.protocol;

/**
 * Specific Jarmil environment such that contains only one class or only one
 * named object. The adding and removeng methods should not be used.
 * 
 * @author martin
 *
 */
public class SingleJarmilEnvironment extends JarmilEnvironment {

	private final Class<?> clazz;
	private final String name;
	private final Object object;

	public SingleJarmilEnvironment(Class<?> clazz) {
		super();

		this.clazz = clazz;
		this.name = null;
		this.object = null;

		super.addClass(clazz);
	}

	public SingleJarmilEnvironment(String name, Object object) {
		super();

		this.clazz = object.getClass();
		this.name = name;
		this.object = object;

		super.addObject(name, object);
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public String getName() {
		return name;
	}

	public Object getObject() {
		return object;
	}

	// @Override
	// public void addClass(Class<?> clazz) {
	// throw new UnsupportedOperationException("adding and removing from single
	// environment");
	// }
	//
	// @Override
	// public void addObject(String name, Object object) {
	// throw new UnsupportedOperationException("adding and removing from single
	// environment");
	// }
	//
	// @Override
	// public void removeClass(Class<?> clazz) {
	// throw new UnsupportedOperationException("adding and removing from single
	// environment");
	// }
	//
	// @Override
	// public void removeObject(String name) {
	// throw new UnsupportedOperationException("adding and removing from single
	// environment");
	// }

	@Override
	public String toString() {
		return "SingleJarmilEnvironment [clazz=" + clazz + ", name=" + name + ", object=" + object + "]";
	}
}
