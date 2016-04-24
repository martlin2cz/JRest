package cz.martlin.jrest.impl.jarmil.target;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Target on the waiter side specifier.
 * 
 * @author martin
 *
 */
public interface TargetOnWaiter extends TargetOfInvocation {

	/**
	 * Invokes given method with given parameters on this target.
	 * 
	 * @param method
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	public Object invoke(Method method, List<Object> parameters) throws Exception;

	/**
	 * Generates the {@link TargetOnGuest} instance corresponding to this
	 * instance.
	 * 
	 * @return
	 */
	public TargetOnGuest toOnGuestTarget();

}
