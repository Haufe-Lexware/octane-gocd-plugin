package com.haufelexware.gocd.service;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;
import java.net.URL;

/**
 * This is the super-class for all requests to the GoCD API.
 * @see <a href="https://api.gocd.org/current">GoCD API</a>
 */
public class GoApiClient {

	private final HttpHost httpHost;
	private final HttpClient httpClient;

	public GoApiClient(URL serverUrl, String username, String password) {
		httpHost = new HttpHost(serverUrl.getHost(), serverUrl.getPort(), serverUrl.getProtocol());

		final CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(username, password));

		httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build();
	}

	public HttpResponse execute(final HttpRequest request) throws IOException {
		return httpClient.execute(httpHost, request);
	}
}
