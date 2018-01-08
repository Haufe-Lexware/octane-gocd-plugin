package com.haufelexware.report.nunit.v25.dom;

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

	@XmlElement(name = "property")
	@XmlElementWrapper(name = "properties")
	private List<NUnitProperty> properties;

	@XmlElements({
		@XmlElement(name = "test-suite", type = NUnitTestSuite.class),
		@XmlElement(name = "test-case", type = NUnitTestCase.class)
	})
	@XmlElementWrapper(name = "results")
	private List<NUnitTest> tests;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<NUnitProperty> getProperties() {
		return properties;
	}

	public void setProperties(List<NUnitProperty> properties) {
		this.properties = properties;
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
