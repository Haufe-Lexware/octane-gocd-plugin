package com.haufelexware.report.junit.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

/**
 * A test failure.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JUnitFailure {

	@XmlAttribute
	private String message;
	@XmlAttribute
	private String type;
	@XmlValue
	private String content;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
