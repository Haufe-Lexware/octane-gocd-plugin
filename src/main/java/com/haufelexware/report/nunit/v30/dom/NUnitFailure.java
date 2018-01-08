package com.haufelexware.report.nunit.v30.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

/**
 * A test failure.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitFailure {

	@XmlElement
	private String message;
	@XmlElement(name = "stack-trace")
	private String stacktrace;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStacktrace() {
		return stacktrace;
	}

	public void setStacktrace(String stacktrace) {
		this.stacktrace = stacktrace;
	}
}
