package se.enfo.mmapp.mq.model;

public class QueueManager {

	private final String name;
	private final int port;
	private final String hostname;

	public QueueManager(final String name, final int port, final String hostname) {
		this.name = name;
		this.port = port;
		this.hostname = hostname;
	}

	public String getName() {
		return name;
	}

	public int getPort() {
		return port;
	}

	public String getHostname() {
		return hostname;
	}

}
