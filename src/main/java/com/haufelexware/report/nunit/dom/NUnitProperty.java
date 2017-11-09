package com.haufelexware.report.nunit.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * A single property.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitProperty {

	@XmlAttribute(required = true)
	private String name;
	@XmlAttribute(required = true)
	private String value;

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
}
