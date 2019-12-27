package macro.buddy.config;

import java.util.Optional;

/**
 * Created by Macro303 on 2019-Nov-29.
 */
public class Connection {
	private String hostName;
	private Integer port;

	public Connection() {
		this(null, null);
	}

	public Connection(String hostName, Integer port) {
		this.hostName = hostName;
		this.port = port;
	}

	public Optional<String> getHostName() {
		return Optional.ofNullable(hostName);
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public Optional<Integer> getPort() {
		return Optional.ofNullable(port);
	}

	public void setPort(Integer port) {
		this.port = port;
	}
}