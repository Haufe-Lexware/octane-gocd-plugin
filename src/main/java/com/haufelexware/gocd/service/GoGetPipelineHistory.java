package com.haufelexware.gocd.service;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GoPipelineHistory;
import com.haufelexware.gocd.dto.GoPipelineInstance;
import com.haufelexware.util.Streams;
import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;

/**
 * This class encapsulates the API call to get all pipeline instances of a Go pipeline.
 * This API service is available since Go Version 14.3.0
 * @see <a href="https://api.gocd.org/current/#get-pipeline-history">Get Pipeline History</a>
 */
public class GoGetPipelineHistory {

	private static final Logger Log = Logger.getLoggerFor(GoGetPipelineHistory.class);

	private final GoApiClient goApiClient;

	public GoGetPipelineHistory(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public List<GoPipelineInstance> get(final String pipelineName) {
		try {
			HttpResponse response = goApiClient.execute(new HttpGet("/go/api/pipelines/" + URLEncoder.encode(pipelineName, "UTF-8") + "/history"));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = Streams.readAsString(response.getEntity().getContent());
				return new Gson().fromJson(content, GoPipelineHistory.class).getPipelines();
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return Collections.emptyList();
	}
}
