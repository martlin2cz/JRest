package cz.martlin.jrest.misc;

/**
 * Represents protocal of communication between guest and waiter.
 * 
 * @author martin
 *
 */
public class CommunicationProtocol {

	private static final String DFLT_EXIT_COMMAND = "quit";
	private static final String DFLT_SERVER_HOST = "localhost";
	private final int port;
	private final String serverHost;
	private final String exitCommand;

	/**
	 * Creates protocol with given port, host of server and command to exit.
	 * 
	 * @param port
	 * @param serverHost
	 * @param exitCommand
	 */
	public CommunicationProtocol(int port, String serverHost, String exitCommand) {
		super();
		this.port = port;
		this.serverHost = serverHost;
		this.exitCommand = exitCommand;
	}

	/**
	 * Creates protocol with given port, host of server and default command to
	 * exit ( {@value #DFLT_EXIT_COMMAND} ).
	 * 
	 * @param port
	 * @param serverHost
	 */
	public CommunicationProtocol(int port, String serverHost) {
		super();
		this.port = port;
		this.serverHost = serverHost;
		this.exitCommand = DFLT_EXIT_COMMAND;
	}

	/**
	 * Creates protocol with given port and default server host (
	 * {@value #DFLT_SERVER_HOST} ) and exit command (
	 * {@value #DFLT_EXIT_COMMAND} ).
	 * 
	 * @param port
	 */
	public CommunicationProtocol(int port) {
		super();
		this.port = port;
		this.serverHost = DFLT_SERVER_HOST;
		this.exitCommand = DFLT_EXIT_COMMAND;
	}

	public int getPort() {
		return port;
	}

	public String getExitCommand() {
		return exitCommand;
	}

	public String getServerHost() {
		return serverHost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exitCommand == null) ? 0 : exitCommand.hashCode());
		result = prime * result + port;
		result = prime * result + ((serverHost == null) ? 0 : serverHost.hashCode());
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
		CommunicationProtocol other = (CommunicationProtocol) obj;
		if (exitCommand == null) {
			if (other.exitCommand != null)
				return false;
		} else if (!exitCommand.equals(other.exitCommand))
			return false;
		if (port != other.port)
			return false;
		if (serverHost == null) {
			if (other.serverHost != null)
				return false;
		} else if (!serverHost.equals(other.serverHost))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommunicationProtocol [port=" + port + ", serverHost=" + serverHost + ", exitCommand=" + exitCommand
				+ "]";
	}

}