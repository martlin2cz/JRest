package cz.martlin.jrest.protocol;

import java.util.Arrays;
import java.util.List;

public class JRestRequest {

	private final String command;
	private final List<String> arguments;

	public JRestRequest(String command, String... arguments) {
		super();
		this.command = command;
		this.arguments = Arrays.asList(arguments);
	}

	public String getCommand() {
		return command;
	}

	public List<String> getArguments() {
		return arguments;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + arguments.hashCode();
		result = prime * result + ((command == null) ? 0 : command.hashCode());
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
		JRestRequest other = (JRestRequest) obj;
		if (!arguments.equals(other.arguments))
			return false;
		if (command == null) {
			if (other.command != null)
				return false;
		} else if (!command.equals(other.command))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "JRestRequest [command=" + command + ", arguments=" + arguments + "]";
	}

}
