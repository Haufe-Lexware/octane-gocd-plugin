package com.haufelexware.gocd.plugin.octane.settings;

import com.haufelexware.gocd.plugin.octane.validation.ValidationIssue;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * This class validates the given {@link OctaneGoCDPluginSettings} for correctness.
 */
public class SettingsValidator {

	public List<ValidationIssue> validate(OctaneGoCDPluginSettings settings) {
		if (settings == null) {
			throw new IllegalArgumentException("settings can not be null");
		}

		final List<ValidationIssue> issues = new ArrayList<>();

		if (settings.getServerURL() == null || settings.getServerURL().isEmpty()) {
			issues.add(new ValidationIssue("serverURL", "Server URL can not be empty"));
		} else { // check the server URL to be a valid URL
			try {
				new URL(settings.getServerURL());
			} catch (MalformedURLException e) {
				issues.add(new ValidationIssue("serverURL", "Server URL is malformed"));
			}
		}

		if (settings.getClientID() == null || settings.getClientID().isEmpty()) {
			issues.add(new ValidationIssue("clientID", "Client ID can not be empty"));
		}

		if (settings.getClientSecret() == null || settings.getClientSecret().isEmpty()) {
			issues.add(new ValidationIssue("clientSecret", "Client Secret can not be empty"));
		}

		return issues;
	}
}
