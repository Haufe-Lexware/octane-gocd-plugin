package com.haufelexware.report.nunit.v25.dom;

import javax.xml.bind.annotation.*;

/**
 * This is the root element of a xml-results-file.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "test-results")
public class NUnitTestResults {

	@XmlAttribute
	private String name;
	@XmlAttribute
	private int total;
	@XmlAttribute
	private int errors;
	@XmlAttribute
	private int failures;
	@XmlAttribute(name = "not-run")
	private int notRun;
	@XmlAttribute
	private int inconclusive;
	@XmlAttribute
	private int ignored;
	@XmlAttribute
	private int skipped;
	@XmlAttribute
	private int invalid;
	@XmlAttribute
	private String date;
	@XmlAttribute
	private String time;

	@XmlElement(name = "environment")
	private NUnitEnvironment environment;
	@XmlElement(name = "culture-info")
	private NUnitCultureInfo cultureInfo;
	@XmlElement(name = "test-suite")
	private NUnitTestSuite testSuite;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getErrors() {
		return errors;
	}

	public void setErrors(int errors) {
		this.errors = errors;
	}

	public int getFailures() {
		return failures;
	}

	public void setFailures(int failures) {
		this.failures = failures;
	}

	public int getNotRun() {
		return notRun;
	}

	public void setNotRun(int notRun) {
		this.notRun = notRun;
	}

	public int getInconclusive() {
		return inconclusive;
	}

	public void setInconclusive(int inconclusive) {
		this.inconclusive = inconclusive;
	}

	public int getIgnored() {
		return ignored;
	}

	public void setIgnored(int ignored) {
		this.ignored = ignored;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public int getInvalid() {
		return invalid;
	}

	public void setInvalid(int invalid) {
		this.invalid = invalid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public NUnitEnvironment getEnvironment() {
		return environment;
	}

	public void setEnvironment(NUnitEnvironment environment) {
		this.environment = environment;
	}

	public NUnitCultureInfo getCultureInfo() {
		return cultureInfo;
	}

	public void setCultureInfo(NUnitCultureInfo cultureInfo) {
		this.cultureInfo = cultureInfo;
	}

	public NUnitTestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(NUnitTestSuite testSuite) {
		this.testSuite = testSuite;
	}
}
