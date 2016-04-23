package cz.martlin.jrest.impl.jarmil.target;

import java.lang.reflect.Method;
import java.util.List;

public interface TargetOnWaiter extends TargetOfInvocation {

	public Object invoke(Method method, List<Object> parameters) throws Exception;
	
	public TargetOnGuest toOnGuestTarget();

}
