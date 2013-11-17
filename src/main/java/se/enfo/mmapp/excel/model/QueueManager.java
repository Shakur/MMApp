package se.enfo.mmapp.excel.model;

public class QueueManager {

	private String name;
	private String sla;
	private String description;
	private String machine;
	private int port;
	private String cluster;
	private String clusterRole;
	private String comment;
	private String decommissioned;
	private String validToYear;

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getSla() {
		return sla;
	}

	public void setSla(final String sla) {
		this.sla = sla;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getMachine() {
		return machine;
	}

	public void setMachine(final String machine) {
		this.machine = machine;
	}

	public int getPort() {
		return port;
	}

	public void setPort(final int port) {
		this.port = port;
	}

	public String getCluster() {
		return cluster;
	}

	public void setCluster(final String cluster) {
		this.cluster = cluster;
	}

	public String getClusterRole() {
		return clusterRole;
	}

	public void setClusterRoll(final String clusterRole) {
		this.clusterRole = clusterRole;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getDecommissioned() {
		return decommissioned;
	}

	public void setDecommissioned(final String decommissioned) {
		this.decommissioned = decommissioned;
	}

	public String getValidToYear() {
		return validToYear;
	}

	public void setValidToYear(final String validToYear) {
		this.validToYear = validToYear;
	}


}
