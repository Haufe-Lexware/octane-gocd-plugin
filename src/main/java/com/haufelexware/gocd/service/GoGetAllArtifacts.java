package com.haufelexware.gocd.service;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GoArtifact;
import com.haufelexware.gocd.dto.GoArtifactList;
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
 * This class encapsulate the request to get all artifacts of a job from Go.
 * This API service is available since Go Version 14.3.0
 * @see <a href="https://api.gocd.org/current/#get-all-artifacts">Get All Artifacts</a>
 *
 * Please notice that this request is no API request (it doesn't begin with "/go/api/").
 * This means Basic-Authentication does not work. The {@link #goApiClient} has to be set
 * up with an authentication cookie. This can be achieved be excuting an API request
 * before this request is done. Since the {@link GoApiClient} is stateful, it will store
 * and reuse any session cookies.
 */
public class GoGetAllArtifacts {

	private static final Logger Log = Logger.getLoggerFor(GoGetAllArtifacts.class);

	private final GoApiClient goApiClient;

	public GoGetAllArtifacts(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	public List<GoArtifact> get(final String pipelineName, final int pipelineCounter, final String stageName, final int stageCounter, final String jobName) {
		try {
			HttpResponse response = goApiClient.execute(new HttpGet("/go/files/" +
				URLEncoder.encode(pipelineName, "UTF-8") + "/" + pipelineCounter + "/" +
				URLEncoder.encode(stageName, "UTF-8") + "/" + stageCounter + "/" +
				URLEncoder.encode(jobName, "UTF-8") + ".json"));
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				String content = Streams.readAsString(response.getEntity().getContent());
				return new Gson().fromJson(content, GoArtifactList.class);
			} else {
				Log.error("Request got HTTP-" + response.getStatusLine().getStatusCode());
			}
		} catch (IOException e) {
			Log.error("Could not perform request", e);
		}
		return Collections.emptyList();
	}
}
