package com.haufelexware.gocd.plugin.octane.converter;

import com.haufelexware.gocd.dto.*;
import com.haufelexware.gocd.service.*;
import com.hp.octane.integrations.dto.events.CIEvent;
import com.hp.octane.integrations.dto.tests.TestRun;
import com.hp.octane.integrations.dto.tests.TestsResult;
import com.thoughtworks.go.plugin.api.logging.Logger;

import java.util.ArrayList;
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
		testResults.addAll(new OctaneJUnitTestResultsBuilder(goApiClient).convert(artifact.getUrl()));
		testResults.addAll(new OctaneNUnit25TestResultsBuilder(goApiClient).convert(artifact.getUrl()));
		testResults.addAll(new OctaneNUnit30TestResultsBuilder(goApiClient).convert(artifact.getUrl()));
		// create some logfile output.
		if (!testResults.isEmpty()) {
			Log.info("artifact '" + artifact.getUrl() + "' could be parsed as test-result-file. Found " + testResults.size() + " tests.");
		} else {
			Log.debug("artifact '" + artifact.getUrl() + "' could not be parsed as test-result-file");
		}
		return testResults;
	}
}
