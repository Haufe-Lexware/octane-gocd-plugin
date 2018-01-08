package com.haufelexware.gocd.service;

import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * This class encapsulates the API call to trigger a specific pipeline to be build in Go.
 * This API service is available since Go Version 14.3.0
 * @see <a href="https://api.gocd.org/current/#scheduling-pipelines">Scheduling Pipelines</a>
 */
public class GoSchedulePipeline {

	private static final Logger Log = Logger.getLoggerFor(GoSchedulePipeline.class);

	private final GoApiClient goApiClient;

	public GoSchedulePipeline(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public boolean trigger(final String pipelineName) {
		try {
			HttpPost request = new HttpPost("/go/api/pipelines/" + URLEncoder.encode(pipelineName, "UTF-8") + "/schedule");
			request.addHeader("Confirm", "true");
			HttpResponse response = goApiClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_ACCEPTED) {
				return true;
			} else {
				Log.error("Request '" + request.getMethod() + " " + request.getURI() + "' got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return false;
	}
}
