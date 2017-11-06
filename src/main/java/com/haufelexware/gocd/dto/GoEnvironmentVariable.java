package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

/**
 * This DTO represents a single environment variable.
 */
public class GoEnvironmentVariable {

	private String name;
	private String value;
	@SerializedName("encrypted_value")
	private String encryptedValue;
	private boolean secure;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getEncryptedValue() {
		return encryptedValue;
	}

	public void setEncryptedValue(String encryptedValue) {
		this.encryptedValue = encryptedValue;
	}

	public boolean isSecure() {
		return secure;
	}

	public void setSecure(boolean secure) {
		this.secure = secure;
	}
}
