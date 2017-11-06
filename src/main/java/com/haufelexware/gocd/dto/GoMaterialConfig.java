package com.haufelexware.gocd.dto;

import java.util.Map;

/**
 * This DTO represents a material config.
 */
public class GoMaterialConfig {

	private String type;
	private Map<String,Object> attributes;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
