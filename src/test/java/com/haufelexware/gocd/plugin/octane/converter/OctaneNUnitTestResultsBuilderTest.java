package com.haufelexware.gocd.plugin.octane.converter;

import com.hp.octane.integrations.dto.tests.TestRun;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * This class ensures that {@link OctaneNUnitTestResultsBuilder} is working correctly.
 */
public class OctaneNUnitTestResultsBuilderTest {

	@Test
	public void testConversionFromNUnitXMLIntoOctaneModel() throws JAXBException {
		final List<TestRun> tests = OctaneNUnitTestResultsBuilder.convert(getClass().getClassLoader().getResourceAsStream("nunit.testResults.xml"));
		Assert.assertNotNull("tests should not be null", tests);
		Assert.assertEquals("number of tests", 6, tests.size());
	}
}
