package com.haufelexware.report.nunit.dom;

import com.haufelexware.report.nunit.BooleanAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Superclass of {@link NUnitTestSuite} and {@link NUnitTestCase}.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitTest {

	@XmlAttribute
	private String name;
	@XmlAttribute
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean executed;
	@XmlAttribute
	private String result;
	@XmlAttribute
	@XmlJavaTypeAdapter(BooleanAdapter.class)
	private Boolean success;
	@XmlAttribute
	private double time;
	@XmlAttribute
	private int asserts;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean wasExecuted() {
		return executed;
	}

	public void setExecuted(Boolean executed) {
		this.executed = executed;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public Boolean wasSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public double getTime() {
		return time;
	}

	public void setTime(double time) {
		this.time = time;
	}

	public int getAsserts() {
		return asserts;
	}

	public void setAsserts(int asserts) {
		this.asserts = asserts;
	}
}
