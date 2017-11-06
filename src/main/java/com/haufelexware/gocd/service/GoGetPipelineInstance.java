package com.haufelexware.gocd.service;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GoPipelineHistory;
import com.haufelexware.gocd.dto.GoPipelineInstance;
import com.haufelexware.gocd.plugin.octane.util.Streams;
import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * This class encapsulate the API call to get a specific pipeline instance of a Go pipeline.
 * This API service is available since Go Version 15.1.0
 * @see <a href="https://api.gocd.org/current/#get-pipeline-instance">Get Pipeline Instance</a>
 */
public class GoGetPipelineInstance {

	private static final Logger Log = Logger.getLoggerFor(GoGetPipelineInstance.class);

	private final GoApiClient goApiClient;

	public GoGetPipelineInstance(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public GoPipelineInstance get(final String pipelineName, final int counter) {
		try {
			HttpResponse response = goApiClient.execute(new HttpGet("/go/api/pipelines/" + URLEncoder.encode(pipelineName, "UTF-8") + "/instance/" + counter));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = Streams.readAsString(response.getEntity().getContent());
				return new Gson().fromJson(content, GoPipelineInstance.class);
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return null;
	}
}
