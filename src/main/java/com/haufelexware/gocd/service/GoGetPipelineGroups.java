package com.haufelexware.gocd.service;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GoPipelineGroup;
import com.haufelexware.gocd.dto.GoPipelineGroups;
import com.haufelexware.util.Streams;
import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

/**
 * This class encapsulate the API call to get all configured pipeline groups from Go.
 * This API service is available since Go Version 14.3.0
 * @see <a href="https://api.gocd.org/17.9.0/#config-listing">Config listing</a>
 */
public class GoGetPipelineGroups {

	private static final Logger Log = Logger.getLoggerFor(GoGetPipelineGroups.class);

	private final GoApiClient goApiClient;

	public GoGetPipelineGroups(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public List<GoPipelineGroup> get() {
		try {
			HttpResponse response = goApiClient.execute(new HttpGet("/go/api/config/pipeline_groups"));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = Streams.readAsString(response.getEntity().getContent());
				return new Gson().fromJson(content, GoPipelineGroups.class);
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return Collections.emptyList();
	}
}
