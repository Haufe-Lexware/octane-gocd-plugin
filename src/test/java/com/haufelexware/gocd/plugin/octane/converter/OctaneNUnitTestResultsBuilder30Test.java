package com.haufelexware.gocd.plugin.octane.converter;

import com.hp.octane.integrations.dto.tests.TestRun;
import com.hp.octane.integrations.dto.tests.TestRunResult;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * This class ensures that {@link OctaneNUnit25TestResultsBuilder} is working correctly.
 */
public class OctaneNUnitTestResultsBuilder30Test {

	@Test
	public void testConversionFromNUnitXMLIntoOctaneModel01() throws JAXBException {
		final List<TestRun> tests = OctaneNUnit30TestResultsBuilder.convert(getClass().getClassLoader().getResourceAsStream("nunit30.testResults.xml"));
		Assert.assertNotNull("tests should not be null", tests);
		Assert.assertEquals("number of tests", 27, tests.size());
		TestRun testRun = tests.get(1);
		Assert.assertEquals("package name", "Haufe.PT.Api.SystemTests.AP.Tests", testRun.getPackageName());
		Assert.assertEquals("class name", "JournalTransactionTests", testRun.getClassName());
		Assert.assertEquals("test name", "GlEntries_ForNonStockItemsWithNoOtherDiscountInPurchaseOrdder_CorrectAccountsArePostedToGl(\"EU\")", testRun.getTestName());
		Assert.assertEquals("test result", TestRunResult.PASSED, testRun.getResult());
	}

	@Test
	public void testConversionFromNUnitXMLIntoOctaneModel02() throws JAXBException {
		final List<TestRun> tests = OctaneNUnit30TestResultsBuilder.convert(getClass().getClassLoader().getResourceAsStream("nunit30.failed.testResults.xml"));
		Assert.assertNotNull("tests should not be null", tests);
		Assert.assertEquals("number of tests", 27, tests.size());
		TestRun testRun = tests.get(1);
		Assert.assertEquals("package name", "Haufe.PT.Api.SystemTests.AP.Tests", testRun.getPackageName());
		Assert.assertEquals("class name", "JournalTransactionTests", testRun.getClassName());
		Assert.assertEquals("test name", "GlEntries_ForNonStockItemsWithNoOtherDiscountInPurchaseOrdder_CorrectAccountsArePostedToGl(\"EU\")", testRun.getTestName());
		Assert.assertEquals("test result", TestRunResult.FAILED, testRun.getResult());
		Assert.assertNotNull("error should not be null", testRun.getError());
		Assert.assertEquals("error message", "GL should contain transactions for following account. 5400\n" +
			"1406\n" +
			"1401\n" +
			"3300\n" +
			"5200\n" +
			"5200\n\n", testRun.getError().getErrorMessage());
		Assert.assertEquals("error stackTrace", "   at Haufe.PT.Api.SystemTests.TestBase.GlAssert.Verify(GlExpectation expected, JournalTransaction actual)\n", testRun.getError().getStackTrace());
	}
}
