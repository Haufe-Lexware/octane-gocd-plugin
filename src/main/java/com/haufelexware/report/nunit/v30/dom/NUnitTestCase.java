package com.haufelexware.report.nunit.v30.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * A culture info.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitTestCase extends NUnitTest {

	@XmlAttribute(name = "methodname")
	private String methodName;
}
