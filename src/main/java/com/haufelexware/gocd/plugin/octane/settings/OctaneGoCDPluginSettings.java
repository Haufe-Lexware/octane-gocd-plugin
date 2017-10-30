package com.haufelexware.gocd.plugin.octane.settings;

/**
 * This POJO carries all plugin settings.
 */
public class OctaneGoCDPluginSettings {

	private String serverURL;
	private String clientID;
	private String clientSecret;
	private String goUsername;
	private String goPassword;

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

	public String getGoUsername() {
		return goUsername;
	}

	public OctaneGoCDPluginSettings setGoUsername(String goUsername) {
		this.goUsername = goUsername;
		return this;
	}

	public String getGoPassword() {
		return goPassword;
	}

	public OctaneGoCDPluginSettings setGoPassword(String goPassword) {
		this.goPassword = goPassword;
		return this;
	}
}
