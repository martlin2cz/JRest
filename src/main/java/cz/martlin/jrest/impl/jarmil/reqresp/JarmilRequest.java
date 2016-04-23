package cz.martlin.jrest.impl.jarmil.reqresp;

import java.util.Arrays;
import java.util.List;

import cz.martlin.jrest.impl.jarmil.handler.JarmilTarget;
import cz.martlin.jrest.impl.jarmil.misc.JarmilEnvironment;
import cz.martlin.jrest.impl.jarmil.target.TargetOnGuest;
import cz.martlin.jrest.impl.jarmil.targets.guest.NewObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.ObjectOnGuestTarget;
import cz.martlin.jrest.impl.jarmil.targets.guest.StaticClassOnGuestTarget;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresp.JRestAbstractRequest;

/**
 * Represents request in Jarmil implementation. Each request has a method name
 * and parameters to be called with. Also contains "a target", which can be
 * simply a class (with static methods) or a name of named object in waiter's
 * {@link JarmilEnvironment}.
 * 
 * @author martin
 *
 */
public class JarmilRequest implements JRestAbstractRequest {

	private final TargetOnGuest target;
	private final String method;
	private final List<Object> parameters;

	/**
	 * If possible, use static fatory methods.
	 * 
	 * @param clazz
	 * @param object
	 * @param method
	 * @param parameters
	 */
	public JarmilRequest(TargetOnGuest target, String method, List<Object> parameters) {
		super();
		this.target = target;
		this.method = method;
		this.parameters = parameters;
	}

	public TargetOnGuest getTarget() {
		return target;
	}

	public String getMethod() {
		return method;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((method == null) ? 0 : method.hashCode());
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
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
		JarmilRequest other = (JarmilRequest) obj;
		if (method == null) {
			if (other.method != null)
				return false;
		} else if (!method.equals(other.method))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JarmilRequest [target=" + target + ", method=" + method + ", parameters=" + parameters + "]";
	}

	/**
	 * 
	 * @param target2
	 * @param method2
	 * @param parameters2
	 * @return
	 */
	public static JarmilRequest create(TargetOnGuest target, String methodName, Object[] parameters) {
		return create(methodName, parameters, target);
	}

	/**
	 * 
	 * @param objectName
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws JRestException
	 */
	public static JarmilRequest createWObjectTarget(String objectName, String methodName, Object... parameters) {
		return create(methodName, parameters, //
				ObjectOnGuestTarget.create(objectName));
	}

	/**
	 * 
	 * @param className
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws JRestException
	 */
	public static JarmilRequest createWStaticClassTarget(String className, String methodName, Object... parameters) {
		return create(methodName, parameters, //
				StaticClassOnGuestTarget.create(className));
	}

	/**
	 * 
	 * @param clazz
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws JRestException
	 */
	public static JarmilRequest createWStaticClassTarget(Class<? extends JarmilTarget> clazz, String methodName,
			Object... parameters) {
		return create(methodName, parameters, //
				StaticClassOnGuestTarget.create(clazz));
	}

	/**
	 * 
	 * @param className
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws JRestException
	 */
	public static JarmilRequest createWNewObjectTarget(String className, String methodName, Object... parameters) {
		return create(methodName, parameters, //
				NewObjectOnGuestTarget.create(className));
	}

	/**
	 * 
	 * @param clazz
	 * @param methodName
	 * @param parameters
	 * @return
	 * @throws JRestException
	 */
	public static JarmilRequest createWNEwObjectClassTarget(Class<? extends JarmilTarget> clazz, String methodName,
			Object... parameters) {
		return create(methodName, parameters, //
				NewObjectOnGuestTarget.create(clazz));
	}

	private static JarmilRequest create(String methodName, Object[] parameters, TargetOnGuest target) {
		List<Object> params = Arrays.asList(parameters);
		return new JarmilRequest(target, methodName, params);
	}

}
