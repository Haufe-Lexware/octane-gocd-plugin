package com.haufelexware.report.nunit;

import com.haufelexware.report.nunit.dom.NUnitTestCase;
import com.haufelexware.report.nunit.dom.NUnitTestResults;
import com.haufelexware.report.nunit.dom.NUnitTestSuite;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Ensure that {@link NUnitReportParser} is working correctly.
 */
public class NUnitReportParserTest {

	@Test
	public void testParsingNUnit35Report() throws JAXBException {
		NUnitTestResults results = new NUnitReportParser().parseFrom(getClass().getClassLoader().getResourceAsStream("nunit.testResults.xml"));
		Assert.assertNotNull("results should not be null", results);
		Assert.assertEquals("run date", "2017-11-02", results.getDate());
		Assert.assertEquals("run time", "09:34:09", results.getTime());
		NUnitTestSuite testSuite = results.getTestSuite();
		Assert.assertNotNull("test-suite should not be null", testSuite);
		Assert.assertEquals("test-suite type", "Assembly", testSuite.getType());
		Assert.assertEquals("test-suite name", "\\go\\pipelines\\pt_build_datageneration_tool\\top\\datageneration\\Haufe.PT.DataGeneration\\test\\Haufe.PT.DataGeneration.Tests\\bin\\Release\\Haufe.PT.DataGeneration.Tests.dll", testSuite.getName());
		Assert.assertEquals("test-suite executed", Boolean.TRUE, testSuite.wasExecuted());
		Assert.assertEquals("test-suite result", "Success", testSuite.getResult());
		Assert.assertEquals("test-suite success", Boolean.TRUE, testSuite.wasSuccess());
		Assert.assertTrue("test-suite time is greater zero", 0 < testSuite.getTime());
		Assert.assertEquals("test-suite number of asserts", 15, testSuite.getAsserts());
		List<NUnitTestCase> testCases = testSuite.getAllTestCases();
		Assert.assertNotNull("testCases should not be null", testCases);
		Assert.assertEquals("number of test cases", 6, testCases.size());
	}
}
