package com.haufelexware.gocd.dto;

/**
 * This DTO represents a property config.
 */
public class GoPropertyConfig {

	private String name;
	private String source;
	private String xpath;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}
}
