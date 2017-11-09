package com.haufelexware.gocd.service;

import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * This class encapsulate the API call to get a single artifact of a job from Go.
 * This API service is available since Go Version 14.3.0
 * @see <a href="https://api.gocd.org/current/#get-artifact-file">Get Artifact</a>
 */
public class GoGetArtifact {

	private static final Logger Log = Logger.getLoggerFor(GoGetArtifact.class);

	private final GoApiClient goApiClient;

	public GoGetArtifact(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public InputStream get(final String artifactUrl) {
		try {
			HttpResponse response = goApiClient.execute(new HttpGet(new URL(artifactUrl).getPath()));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				return response.getEntity().getContent();
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return null;
	}
}
