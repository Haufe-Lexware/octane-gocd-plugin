package com.haufelexware.report.nunit.v30.dom;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

/**
 * A culture info.
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class NUnitTestSuite extends NUnitTest {

	@XmlAttribute
	private String type;
	@XmlAttribute
	private int total;
	@XmlAttribute
	private int passed;
	@XmlAttribute
	private int failed;
	@XmlAttribute
	private int inconclusive;
	@XmlAttribute
	private int skipped;

	@XmlElements({
		@XmlElement(name = "test-suite", type = NUnitTestSuite.class),
		@XmlElement(name = "test-case", type = NUnitTestCase.class)
	})
	private List<NUnitTest> tests;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getPassed() {
		return passed;
	}

	public void setPassed(int passed) {
		this.passed = passed;
	}

	public int getFailed() {
		return failed;
	}

	public void setFailed(int failed) {
		this.failed = failed;
	}

	public int getInconclusive() {
		return inconclusive;
	}

	public void setInconclusive(int inconclusive) {
		this.inconclusive = inconclusive;
	}

	public int getSkipped() {
		return skipped;
	}

	public void setSkipped(int skipped) {
		this.skipped = skipped;
	}

	public List<NUnitTest> getTests() {
		return tests;
	}

	public void setTests(List<NUnitTest> tests) {
		this.tests = tests;
	}

	public List<NUnitTestCase> getAllTestCases() {
		final List<NUnitTestCase> cases = new ArrayList<>();
		for (NUnitTest test : tests) {
			if (test instanceof NUnitTestSuite) {
				cases.addAll(((NUnitTestSuite)test).getAllTestCases());
			} else if (test instanceof NUnitTestCase) {
				cases.add((NUnitTestCase)test);
			}
		}
		return cases;
	}
}
