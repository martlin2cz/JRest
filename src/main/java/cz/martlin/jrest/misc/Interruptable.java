package cz.martlin.jrest.misc;

/**
 * Represents something, which can be interrupted.
 * 
 * @author martin
 *
 */
public interface Interruptable {
	/**
	 * Marks the service as interrupted and the service should ASAP stop.
	 */
	public void interrupt();
}
