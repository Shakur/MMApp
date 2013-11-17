package se.enfo.mmapp.excel.model;

public class MQMachine {

	private String node;
	private String dns;
	private String ip;
	private String description;
	private int processors;
	private int totalCores;
	private int pvu;
	private int extendedPvu;
	private String osVersion;
	private String mqVersion;
	private String candleVersion;
	private String comment;
	private String decommissioned;
	private String status;
	private String installed;

	public String getNode() {
		return node;
	}

	public void setNode(final String node) {
		this.node = node;
	}

	public String getDns() {
		return dns;
	}

	public void setDns(final String dns) {
		this.dns = dns;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(final String ip) {
		this.ip = ip;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public int getProcessors() {
		return processors;
	}

	public void setProcessors(final int processors) {
		this.processors = processors;
	}

	public int getTotalCores() {
		return totalCores;
	}

	public void setTotalCores(final int totalCores) {
		this.totalCores = totalCores;
	}

	public int getPvu() {
		return pvu;
	}

	public void setPvu(final int pvu) {
		this.pvu = pvu;
	}

	public int getExtendedPvu() {
		return extendedPvu;
	}

	public void setExtendedPvu(final int extendedPvu) {
		this.extendedPvu = extendedPvu;
	}

	public String getOsVersion() {
		return osVersion;
	}

	public void setOsVersion(final String osVersion) {
		this.osVersion = osVersion;
	}

	public String getMqVersion() {
		return mqVersion;
	}

	public void setMqVersion(final String mqVersion) {
		this.mqVersion = mqVersion;
	}

	public String getCandleVersion() {
		return candleVersion;
	}

	public void setCandleVersion(final String candleVersion) {
		this.candleVersion = candleVersion;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getDecommisioned() {
		return decommissioned;
	}

	public void setDecommissioned(final String decommissioned) {
		this.decommissioned = decommissioned;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(final String status) {
		this.status = status;
	}

	public String getInstalled() {
		return installed;
	}

	public void setInstalled(final String installed) {
		this.installed = installed;
	}

}
