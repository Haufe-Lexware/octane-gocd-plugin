package com.haufelexware.gocd.service;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GoPipelineConfig;
import com.haufelexware.util.Streams;
import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * This class encapsulate the API call to get an complete pipeline configuration from Go.
 * This API service is available since Go Version 15.3.0
 * @see <a href="https://api.gocd.org/current/#get-pipeline-config">Get Pipeline Config</a>
 */
public class GoGetPipelineConfig {

	private static final Logger Log = Logger.getLoggerFor(GoGetPipelineConfig.class);

	private final GoApiClient goApiClient;

	public GoGetPipelineConfig(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public GoPipelineConfig get(final String pipelineName) {
		try {
			HttpGet request = new HttpGet("/go/api/admin/pipelines/" + URLEncoder.encode(pipelineName, "UTF-8"));
			request.addHeader("Accept", "application/vnd.go.cd.v4+json");
			HttpResponse response = goApiClient.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = Streams.readAsString(response.getEntity().getContent());
				return new Gson().fromJson(content, GoPipelineConfig.class);
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return null;
	}
}
