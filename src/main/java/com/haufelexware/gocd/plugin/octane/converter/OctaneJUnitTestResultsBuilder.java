package com.haufelexware.gocd.plugin.octane.converter;

import com.haufelexware.gocd.service.GoApiClient;
import com.haufelexware.gocd.service.GoGetArtifact;
import com.haufelexware.report.junit.JUnitReportParser;
import com.haufelexware.report.junit.dom.JUnitFailure;
import com.haufelexware.report.junit.dom.JUnitTestCase;
import com.haufelexware.report.junit.dom.JUnitTestSuite;
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
 * This builder helps converting junit test reports into Octane {@link TestsResult}.
 */
public class OctaneJUnitTestResultsBuilder {

	protected static final Logger Log = Logger.getLoggerFor(OctaneJUnitTestResultsBuilder.class);

	private final GoApiClient goApiClient;

	public OctaneJUnitTestResultsBuilder(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public List<TestRun> convert(String artifactUrl) {
		final List<TestRun> testResults = new ArrayList<>();
		try (InputStream report = new GoGetArtifact(goApiClient).get(artifactUrl)) { // try to parse the given artifact as JUnit report file.
			return convert(report);
		} catch (JAXBException e) {
			Log.info("parsing artifact '" + artifactUrl + "' as JUnit report failed");
		} catch (IOException e) {
			Log.error("could not read artifact '" + artifactUrl + "' from server", e);
		}
		return testResults;
	}

	public static List<TestRun> convert(InputStream artifactStream) throws JAXBException {
		final List<TestRun> testResults = new ArrayList<>();
		JUnitTestSuite testSuite = new JUnitReportParser().parseFrom(artifactStream);
		if (testSuite != null) {
			List<JUnitTestCase> testCases = testSuite.getTestCases();
			long startTime;
			try {
				startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(testSuite.getTimestamp()).getTime();
			} catch (ParseException e) {
				Log.warn("Could not parse timestamp '" + testSuite.getTimestamp() + "' using current timestamp instead");
				startTime = new Date().getTime();
			}
			for (JUnitTestCase testCase : testCases) {
				testResults.add(DTOFactory.getInstance().newDTO(TestRun.class)
					//.setClassName(testCase.getClassName())
					.setDuration((long)(testCase.getTime() * 1000))
					.setTestName(testCase.getName())
					.setStarted(startTime)
					.setResult(convert(testCase))
					//.setModuleName(testSuite.getName())
					.setPackageName(extractPackageName(testSuite.getName()))
					.setError(extractTestRunError(testCase)));
			}
		}
		return testResults;
	}

	public static TestRunResult convert(JUnitTestCase testCase) {
		if (testCase.wasSkipped()) {
			return TestRunResult.SKIPPED;
		} else if (testCase.getFailures() != null && !testCase.getFailures().isEmpty()) {
			return TestRunResult.FAILED;
		} else {
			return TestRunResult.PASSED;
		}
	}

	public static String extractPackageName(String className) {
		int index = className.lastIndexOf(".");
		return index > -1 ? className.substring(0, index) : className;
	}

	public static TestRunError extractTestRunError(JUnitTestCase testCase) {
		if (testCase.getFailures() != null && !testCase.getFailures().isEmpty()) {
			JUnitFailure failure = testCase.getFailures().get(0);
			return DTOFactory.getInstance().newDTO(TestRunError.class)
				.setErrorMessage(failure.getMessage())
				.setErrorType(failure.getType())
				.setStackTrace(failure.getContent());
		}
		return null;
	}
}
