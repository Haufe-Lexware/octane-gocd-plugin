package com.haufelexware.report.junit;

import com.haufelexware.report.junit.dom.JUnitTestCase;
import com.haufelexware.report.junit.dom.JUnitTestSuite;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * Ensure that {@link JUnitReportParser} is working correctly.
 */
public class JUnitReportParserTest {

	@Test
	public void testParsingJUnit4Report() throws JAXBException {
		JUnitTestSuite testSuite = new JUnitReportParser().parseFrom(getClass().getClassLoader().getResourceAsStream("junit.testResults.xml"));
		Assert.assertNotNull("testSuite should not be null", testSuite);
		Assert.assertEquals("testSuite name", "com.haufelexware.report.junit.JUnitReportParserTest", testSuite.getName());
		Assert.assertEquals("testSuite hostname", "hypo", testSuite.getHostname());
		Assert.assertEquals("testSuite timestamp", "2017-11-08T13:13:06", testSuite.getTimestamp());
		Assert.assertTrue("testSuite time is greater zero", 0 < testSuite.getTime());
		List<JUnitTestCase> testCase = testSuite.getTestCases();
		Assert.assertNotNull("testCase should not be null", testCase);
		Assert.assertFalse("testCase should not be empty", testCase.isEmpty());
	}
}
