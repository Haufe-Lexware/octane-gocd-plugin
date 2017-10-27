package com.haufelexware.gocd.plugin.octane.settings;

/**
 * This POJO carries all plugin settings.
 */
public class OctaneGoCDPluginSettings {

	private String serverURL;
	private String clientID;
	private String clientSecret;

	public String getServerURL() {
		return serverURL;
	}

	public OctaneGoCDPluginSettings setServerURL(String serverURL) {
		this.serverURL = serverURL;
		return this;
	}

	public String getClientID() {
		return clientID;
	}

	public OctaneGoCDPluginSettings setClientID(String clientID) {
		this.clientID = clientID;
		return this;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public OctaneGoCDPluginSettings setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
		return this;
	}
}
