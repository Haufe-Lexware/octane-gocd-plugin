package com.haufelexware.report.junit.dom;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

/**
 * A single test case.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class JUnitTestCase {

	@XmlAttribute(required = true)
	private String name;
	@XmlAttribute(name = "classname", required = true)
	private String className;
	@XmlAttribute(required = true)
	private double time;

	@XmlElement
	private JUnitSkipped skipped;
	@XmlElement(name = "failure")
	private List<JUnitFailure> failures;

	@XmlElement(name = "system-out")
	private String systemOut;
	@XmlElement(name = "system-err")
	private String systemError;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public List<JUnitFailure> getFailures() {
		return failures;
	}

	public void setFailures(List<JUnitFailure> failures) {
		this.failures = failures;
	}

	public boolean wasSkipped() {
		return skipped != null;
	}

	/**
	 * When using gradle systemOut will only exist when
	 * <code>
	 *     test {
	 *         reports.junitXml.outputPerTestCase = true
	 *     }
	 * </code>
	 * is set.
	 */
	public String getSystemOut() {
		return systemOut;
	}

	public void setSystemOut(String systemOut) {
		this.systemOut = systemOut;
	}

	/**
	 * When using gradle systemError will only exist when
	 * <code>
	 *     test {
	 *         reports.junitXml.outputPerTestCase = true
	 *     }
	 * </code>
	 * is set.
	 */
	public String getSystemError() {
		return systemError;
	}

	public void setSystemError(String systemError) {
		this.systemError = systemError;
	}
}
