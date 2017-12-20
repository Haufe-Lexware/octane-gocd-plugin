package com.haufelexware.gocd.plugin.octane.converter;

import com.haufelexware.gocd.service.GoApiClient;
import com.haufelexware.gocd.service.GoGetArtifact;
import com.haufelexware.report.nunit.NUnitReportParser;
import com.haufelexware.report.nunit.dom.NUnitFailure;
import com.haufelexware.report.nunit.dom.NUnitTestCase;
import com.haufelexware.report.nunit.dom.NUnitTestResults;
import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.tests.TestRun;
import com.hp.octane.integrations.dto.tests.TestRunError;
import com.hp.octane.integrations.dto.tests.TestRunResult;
import com.hp.octane.integrations.dto.tests.TestsResult;
import com.thoughtworks.go.plugin.api.logging.Logger;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * This builder helps converting nUnit test reports into Octane {@link TestsResult}.
 */
public class OctaneNUnitTestResultsBuilder {

	protected static final Logger Log = Logger.getLoggerFor(OctaneNUnitTestResultsBuilder.class);

	private final GoApiClient goApiClient;

	public OctaneNUnitTestResultsBuilder(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public List<TestRun> convert(String artifactUrl) {
		final List<TestRun> testRuns = new ArrayList<>();
		try (InputStream report = new GoGetArtifact(goApiClient).get(artifactUrl)) { // try to parse the given artifact as JUnit report file.
			return convert(report);
		} catch (JAXBException e) {
			Log.info("parsing artifact '" + artifactUrl + "' as NUnit report failed");
		} catch (IOException e) {
			Log.error("could not read artifact '" + artifactUrl + "' from server", e);
		}
		return testRuns;
	}

	public static List<TestRun> convert(InputStream artifactStream) throws JAXBException {
		final List<TestRun> testRuns = new ArrayList<>();
		NUnitTestResults testResults = new NUnitReportParser().parseFrom(artifactStream);
		if (testResults != null && testResults.getTestSuite() != null) {
			List<NUnitTestCase> testCases = testResults.getTestSuite().getAllTestCases();
			long startTime;
			try {
				startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(testResults.getDate() + "T" + testResults.getTime()).getTime();
			} catch (ParseException e) {
				Log.warn("Could not parse timestamp '" + testResults.getDate() + "T" + testResults.getTime() + "' using current timestamp instead");
				startTime = new Date().getTime();
			}
			for (NUnitTestCase testCase : testCases) {
				testRuns.add(DTOFactory.getInstance().newDTO(TestRun.class)
					.setTestName(testCase.getName())
					.setDuration((long)(testCase.getTime() * 1000))
					.setStarted(startTime)
					.setResult(convert(testCase))
					.setPackageName(extractPackageName(testCase.getName()))
					.setError(extractTestRunError(testCase)));
			}
		}
		return testRuns;
	}

	public static TestRunResult convert(NUnitTestCase testCase) {
		if (!testCase.wasExecuted()) {
			return TestRunResult.SKIPPED;
		} else if (testCase.wasSuccess()) {
			return TestRunResult.PASSED;
		} else {
			return TestRunResult.FAILED;
		}
	}

	public static String extractPackageName(String className) {
		int index = className.lastIndexOf(".");
		return index > -1 ? className.substring(0, index) : className;
	}

	public static TestRunError extractTestRunError(NUnitTestCase testCase) {
		NUnitFailure failure = testCase.getFailure();
		if (failure != null) {
			return DTOFactory.getInstance().newDTO(TestRunError.class)
				.setErrorMessage(failure.getMessage())
				.setStackTrace(failure.getStacktrace());
		}
		return null;
	}
}
