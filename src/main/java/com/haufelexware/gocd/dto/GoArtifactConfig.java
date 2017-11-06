package com.haufelexware.gocd.dto;

/**
 * This DTO represents an artifact config.
 */
public class GoArtifactConfig {

	private String type;
	private String source;
	private String destination;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}
}
