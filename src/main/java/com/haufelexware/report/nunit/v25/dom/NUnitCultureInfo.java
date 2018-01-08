package com.haufelexware.report.nunit.v25.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * A culture info.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitCultureInfo {

	@XmlAttribute(name = "current-culture")
	private String currentCulture;
	@XmlAttribute(name = "current-uiculture")
	private String currentUICulture;
}
