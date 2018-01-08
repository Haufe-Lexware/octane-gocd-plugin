package com.haufelexware.report.nunit.v30.dom;

import javax.xml.bind.annotation.*;
import java.util.List;

/**
 * Superclass of {@link NUnitTestSuite} and {@link NUnitTestCase}.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitTest {

	@XmlAttribute
	private String id;
	@XmlAttribute
	private String name;
	@XmlAttribute(name = "fullname")
	private String fullName;
	@XmlAttribute(name = "classname")
	private String className;
	@XmlAttribute(name = "testcasecount")
	private int testCaseCount;
	@XmlAttribute(name = "runstate")
	private NUnitRunState runState;
	@XmlAttribute
	private NUnitTestResult result;
	@XmlAttribute
	private String label;
	@XmlAttribute
	private String site;
	@XmlAttribute(name = "start-time")
	private String startTime;
	@XmlAttribute(name = "end-time")
	private String endTime;
	@XmlAttribute
	private double duration;
	@XmlAttribute
	private int asserts;

	@XmlElement
	private NUnitFailure failure;

	@XmlElement(name = "property")
	@XmlElementWrapper(name = "properties")
	private List<NUnitProperty> properties;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getTestCaseCount() {
		return testCaseCount;
	}

	public void setTestCaseCount(int testCaseCount) {
		this.testCaseCount = testCaseCount;
	}

	public NUnitRunState getRunState() {
		return runState;
	}

	public void setRunState(NUnitRunState runState) {
		this.runState = runState;
	}

	public NUnitTestResult getResult() {
		return result;
	}

	public void setResult(NUnitTestResult result) {
		this.result = result;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public double getDuration() {
		return duration;
	}

	public void setDuration(double duration) {
		this.duration = duration;
	}

	public int getAsserts() {
		return asserts;
	}

	public void setAsserts(int asserts) {
		this.asserts = asserts;
	}

	public NUnitFailure getFailure() {
		return failure;
	}

	public void setFailure(NUnitFailure failure) {
		this.failure = failure;
	}

	public List<NUnitProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<NUnitProperty> properties) {
		this.properties = properties;
	}
}
