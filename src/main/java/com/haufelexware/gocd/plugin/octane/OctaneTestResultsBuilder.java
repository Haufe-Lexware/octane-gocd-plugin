package com.haufelexware.gocd.plugin.octane;

import com.haufelexware.gocd.dto.*;
import com.haufelexware.gocd.service.*;
import com.haufelexware.report.junit.JUnitReportParser;
import com.haufelexware.report.junit.dom.JUnitTestCase;
import com.haufelexware.report.junit.dom.JUnitTestSuite;
import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.events.CIEvent;
import com.hp.octane.integrations.dto.tests.TestRun;
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
 * This builder helps converting test reports into Octane {@link TestsResult}.
 */
public class OctaneTestResultsBuilder {

	protected static final Logger Log = Logger.getLoggerFor(OctaneTestResultsBuilder.class);

	private final GoApiClient goApiClient;

	public OctaneTestResultsBuilder(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	/**
	 * This method will analyze the given {@param statusInfo} and may or may not generate
	 * and send a {@link CIEvent} to Octane.
	 */
	public List<TestRun> convert(List<GoArtifact> artifacts) {
		final List<TestRun> testResults = new ArrayList<>();
		if (artifacts != null) {
			for (GoArtifact artifact : artifacts) {
				if ("folder".equals(artifact.getType())) {
					testResults.addAll(convert(artifact.getFiles()));
				} else if ("file".equals(artifact.getType()) && artifact.getName() != null && artifact.getName().toLowerCase().endsWith(".xml")) {
					testResults.addAll(convert(artifact));
				}
			}
		}
		return testResults;
	}

	public List<TestRun> convert(GoArtifact artifact) {
		final List<TestRun> testResults = new ArrayList<>();
		try (InputStream report = new GoGetArtifact(goApiClient).get(artifact.getUrl())) { // try to parse the given artifact as JUnit report file.
			JUnitTestSuite testSuite = new JUnitReportParser().parseFrom(report);
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
						.setClassName(testCase.getClassName())
						.setDuration((long)(testCase.getTime() * 1000))
						.setTestName(testCase.getName())
						.setStarted(startTime)
						.setResult(convert(testCase))
						.setModuleName(testSuite.getName())
						.setPackageName(extractPackageName(testSuite.getName())));
				}
			}
		} catch (JAXBException e) {
			Log.info("parsing xml file as JUnit report failed");
		} catch (IOException e) {
			Log.error("could not read artifact '" + artifact.getUrl() + "' from server", e);
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
}
