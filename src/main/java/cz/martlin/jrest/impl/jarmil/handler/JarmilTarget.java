package cz.martlin.jrest.impl.jarmil.handler;

import java.io.File;

import cz.martlin.jrest.impl.jarmil.misc.JarmilEnvironment;

/**
 * The target of invocation of methods over the Jarmil JRest implementation.
 * Each class which is supposed to be handler of by-guard-sent requests
 * <strong>must</strong> implement this interface. The reason is primarly the
 * security.
 * 
 * If the Jarmil will allow to invoke methods on all classes, the guest could
 * uncontrollable invoke {@link File#createTempFile(String, String)} thousands
 * times.
 * 
 * When the particular target of guest is declared, the second step is to
 * register it as a target in the {@link JarmilEnvironment}'s instance.
 * 
 * @author martin
 * @see JarmilEnvironment
 *
 */
public interface JarmilTarget {

	/**
	 * (Optional) For debug and testing purposes this method could return some
	 * description about this target.
	 * 
	 * @return
	 */
	public String getJarmilTargetDescription();
}
