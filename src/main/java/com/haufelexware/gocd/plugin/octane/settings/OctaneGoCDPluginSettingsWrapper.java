package com.haufelexware.gocd.plugin.octane.settings;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * This wrapper is used to render the response of <a href="https://plugin-api.gocd.org/current/notifications/#get-plugin-settings">Get Plugin Settings</a>
 */
public class OctaneGoCDPluginSettingsWrapper {

	@SerializedName("plugin-settings")
	private Map<String, Object> properties;

	/**
	 * This method renders the given properties into a settings POJO.
	 */
	public OctaneGoCDPluginSettings getPluginSettings() {
		return new OctaneGoCDPluginSettings()
			.setServerURL((String)getValueFor("serverURL"))
			.setClientID((String)getValueFor("clientID"))
			.setClientSecret((String)getValueFor("clientSecret"))
			.setGoUsername((String)getValueFor("goUsername"))
			.setGoPassword((String)getValueFor("goPassword"));
	}

	protected Object getValueFor(final String property) {
		final Object valueMap = properties.get(property);
		if (valueMap != null && valueMap instanceof Map) {
			return ((Map)valueMap).get("value");

		}
		return null;
	}
}
