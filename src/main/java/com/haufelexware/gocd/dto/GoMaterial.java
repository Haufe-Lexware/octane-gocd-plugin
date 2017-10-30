package com.haufelexware.gocd.dto;

/**
 * DTO for a material in Go.
 */
public class GoMaterial {

	private String type;
	private String description;
	private String fingerprint;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFingerprint() {
		return fingerprint;
	}

	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
}
