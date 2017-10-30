package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

/**
 * This DTO resembles the service response of <a href="https://plugin-api.gocd.org/current/notifications/#get-server-info">Get Server Info</a>
 */
public class GoServerInfo {

	@SerializedName("server_id")
	private String serverID;
	@SerializedName("site_url")
	private String siteURL;
	@SerializedName("secure_site_url")
	private String secureSiteURL;

	public String getServerID() {
		return serverID;
	}

	public void setServerID(String serverID) {
		this.serverID = serverID;
	}

	public String getSiteURL() {
		return siteURL;
	}

	public void setSiteURL(String siteURL) {
		this.siteURL = siteURL;
	}

	public String getSecureSiteURL() {
		return secureSiteURL;
	}

	public void setSecureSiteURL(String secureSiteURL) {
		this.secureSiteURL = secureSiteURL;
	}
}
