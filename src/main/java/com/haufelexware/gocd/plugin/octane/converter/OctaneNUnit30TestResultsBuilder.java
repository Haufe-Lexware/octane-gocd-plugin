package com.haufelexware.gocd.plugin.octane.converter;

import com.haufelexware.gocd.service.GoApiClient;
import com.haufelexware.gocd.service.GoGetArtifact;
import com.haufelexware.report.nunit.v30.NUnit30ReportParser;
import com.haufelexware.report.nunit.v30.dom.NUnitFailure;
import com.haufelexware.report.nunit.v30.dom.NUnitTestCase;
import com.haufelexware.report.nunit.v30.dom.NUnitTestRun;
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
public class OctaneNUnit30TestResultsBuilder {

	protected static final Logger Log = Logger.getLoggerFor(OctaneNUnit30TestResultsBuilder.class);

	private final GoApiClient goApiClient;

	public OctaneNUnit30TestResultsBuilder(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public List<TestRun> convert(String artifactUrl) {
		final List<TestRun> testRuns = new ArrayList<>();
		try (InputStream report = new GoGetArtifact(goApiClient).get(artifactUrl)) { // try to parse the given artifact as JUnit report file.
			return convert(report);
		} catch (JAXBException e) {
			Log.debug("parsing artifact '" + artifactUrl + "' as NUnit(3.0) report failed");
		} catch (IOException e) {
			Log.error("could not read artifact '" + artifactUrl + "' from server", e);
		}
		return testRuns;
	}

	public static List<TestRun> convert(InputStream artifactStream) throws JAXBException {
		final List<TestRun> testRuns = new ArrayList<>();
		NUnitTestRun testRun = new NUnit30ReportParser().parseFrom(artifactStream);
		if (testRun != null) {
			List<NUnitTestCase> testCases = testRun.getAllTestCases();
			long startTime;
			try {
				startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX").parse(testRun.getStartTime()).getTime();
			} catch (ParseException e) {
				Log.warn("Could not parse timestamp '" + testRun.getStartTime() + "' using current timestamp instead");
				startTime = new Date().getTime();
			}
			for (NUnitTestCase testCase : testCases) {
				testRuns.add(DTOFactory.getInstance().newDTO(TestRun.class)
					.setPackageName(extractPackageName(testCase.getClassName()))
					.setClassName(extractSimpleClassName(testCase.getClassName()))
					.setTestName(testCase.getName())
					.setDuration((long)(testCase.getDuration() * 1000))
					.setStarted(startTime)
					.setResult(convert(testCase))
					.setError(extractTestRunError(testCase)));
			}
		}
		return testRuns;
	}

	public static TestRunResult convert(NUnitTestCase testCase) {
		switch (testCase.getResult()) {
			case Passed: return TestRunResult.PASSED;
			case Failed: return TestRunResult.FAILED;
			default: return TestRunResult.SKIPPED; // covers the states Skipped and Inconclusive.
		}
	}

	public static String extractSimpleClassName(String fullyQualifiedClassName) {
		int index = fullyQualifiedClassName.lastIndexOf(".");
		return index > -1 ? fullyQualifiedClassName.substring(index + 1) : fullyQualifiedClassName;
	}

	public static String extractPackageName(String fullyQualifiedClassName) {
		int index = fullyQualifiedClassName.lastIndexOf(".");
		return index > -1 ? fullyQualifiedClassName.substring(0, index) : fullyQualifiedClassName;
	}

	public static String extractTestName(String testCaseName) {
		int index = testCaseName.lastIndexOf(".");
		return index > -1 ? testCaseName.substring(index + 1) : testCaseName;
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
