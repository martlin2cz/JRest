package cz.martlin.jrest.waiter;

public interface CommandProcessor {

	public abstract String handleCommand(String command) throws Exception;
}
