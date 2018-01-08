package com.haufelexware.gocd.plugin.octane.converter;

import com.hp.octane.integrations.dto.tests.TestRun;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * This class ensures that {@link OctaneNUnit25TestResultsBuilder} is working correctly.
 */
public class OctaneNUnitTestResultsBuilder25Test {

	@Test
	public void testConversionFromNUnitXMLIntoOctaneModel() throws JAXBException {
		final List<TestRun> tests = OctaneNUnit25TestResultsBuilder.convert(getClass().getClassLoader().getResourceAsStream("nunit25.testResults.xml"));
		Assert.assertNotNull("tests should not be null", tests);
		Assert.assertEquals("number of tests", 6, tests.size());
		TestRun testRun = tests.get(0);
		Assert.assertEquals("package name", "Haufe.PT.DataGeneration.Tests", testRun.getPackageName());
		Assert.assertEquals("class name", "CommandLineOptionsTests", testRun.getClassName());
		Assert.assertEquals("test name", "TestParseGenerators_MultipleGenerators_OneIncorrect", testRun.getTestName());
	}
}
