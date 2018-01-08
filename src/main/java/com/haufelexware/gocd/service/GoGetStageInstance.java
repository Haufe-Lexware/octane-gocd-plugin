package com.haufelexware.gocd.service;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GoStageInstance;
import com.haufelexware.util.Streams;
import com.thoughtworks.go.plugin.api.logging.Logger;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * This class encapsulates the API call to get a specific stage instance.
 * This API service is available since Go Version 15.1.0
 * @see <a href="https://api.gocd.org/current/#get-stage-instance">Get Stage Instance</a>
 */
public class GoGetStageInstance {

	private static final Logger Log = Logger.getLoggerFor(GoGetStageInstance.class);

	private final GoApiClient goApiClient;

	public GoGetStageInstance(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public GoStageInstance get(final String pipelineName, final int pipelineCounter, final String stageName, final int stageCounter) {
		try {
			HttpResponse response = goApiClient.execute(new HttpGet("/go/api/stages/" +
				URLEncoder.encode(pipelineName, "UTF-8") + "/" +
				URLEncoder.encode(stageName, "UTF-8") + "/instance/" + pipelineCounter + "/" + stageCounter));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = Streams.readAsString(response.getEntity().getContent());
				return new Gson().fromJson(content, GoStageInstance.class);
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return null;
	}
}
