package com.haufelexware.gocd.service;

import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/**
 * This class uses the REST-service for querying all configured pipeline groups as a test
 * to check whether the go-user-credentials are correct. The API service which is used here
 * is available since Go Version 14.3.0
 * @see <a href="https://api.gocd.org/current/#get-pipeline-config">Get Pipeline Config</a>
 */
public class GoGetPipelineGroupsAsTest {

	private static final Logger Log = Logger.getLoggerFor(GoGetPipelineGroupsAsTest.class);

	private final GoApiClient goApiClient;

	public GoGetPipelineGroupsAsTest(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public HttpResponse getHttpResponse() {
		try {
			return goApiClient.execute(new HttpGet("/go/api/config/pipeline_groups"));
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return null;
	}
}
