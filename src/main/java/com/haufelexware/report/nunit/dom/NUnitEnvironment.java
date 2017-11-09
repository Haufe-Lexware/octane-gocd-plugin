package com.haufelexware.report.nunit.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * A culture info.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitEnvironment {

	@XmlAttribute(name = "nunit-version")
	private String nunitVersion;
	@XmlAttribute(name = "clr-version")
	private String clrVersion;
	@XmlAttribute(name = "os-version")
	private String osVersion;
	@XmlAttribute
	private String platform;
	@XmlAttribute
	private String cwd;
	@XmlAttribute(name = "machine-name")
	private String machineName;
	@XmlAttribute
	private String user;
	@XmlAttribute(name = "user-domain")
	private String userDomain;
}
