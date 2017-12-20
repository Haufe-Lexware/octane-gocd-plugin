package com.haufelexware.gocd.plugin.octane.converter;

import com.hp.octane.integrations.dto.tests.TestRun;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * This class ensures that {@link OctaneJUnitTestResultsBuilder} is working correctly.
 */
public class OctaneJUnitTestResultsBuilderTest {

	@Test
	public void testConversionFromJUnitXMLIntoOctaneModel() throws JAXBException {
		final List<TestRun> tests = OctaneJUnitTestResultsBuilder.convert(getClass().getClassLoader().getResourceAsStream("junit.testResults.xml"));
		Assert.assertNotNull("tests should not be null", tests);
		Assert.assertEquals("number of tests", 3, tests.size());
		TestRun testRun = tests.get(0);
		Assert.assertEquals("package name", "com.haufelexware.report.junit", testRun.getPackageName());
		Assert.assertEquals("class name", "JUnitReportParserTest", testRun.getClassName());
		Assert.assertEquals("test name", "testComparison", testRun.getTestName());

	}
}
