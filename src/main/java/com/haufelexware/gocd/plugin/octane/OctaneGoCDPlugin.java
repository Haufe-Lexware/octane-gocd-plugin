package com.haufelexware.gocd.plugin.octane;

import com.google.gson.Gson;
import com.haufelexware.gocd.dto.GenericJsonObject;
import com.haufelexware.gocd.dto.GoServerInfo;
import com.haufelexware.gocd.plugin.octane.converter.OctaneCIEventBuilder;
import com.haufelexware.gocd.plugin.octane.settings.OctaneGoCDPluginSettings;
import com.haufelexware.gocd.plugin.octane.settings.OctaneGoCDPluginSettingsWrapper;
import com.haufelexware.gocd.plugin.octane.settings.SettingsValidator;
import com.haufelexware.util.MapBuilder;
import com.haufelexware.util.Streams;
import com.haufelexware.gocd.plugin.octane.validation.ValidationIssue;
import com.haufelexware.octane.GoPluginServices;
import com.hp.octane.integrations.OctaneSDK;
import com.hp.octane.integrations.dto.configuration.OctaneConfiguration;
import com.hp.octane.integrations.dto.connectivity.OctaneResponse;
import com.thoughtworks.go.plugin.api.GoApplicationAccessor;
import com.thoughtworks.go.plugin.api.GoPlugin;
import com.thoughtworks.go.plugin.api.GoPluginIdentifier;
import com.thoughtworks.go.plugin.api.annotation.Extension;
import com.thoughtworks.go.plugin.api.exceptions.UnhandledRequestTypeException;
import com.thoughtworks.go.plugin.api.logging.Logger;
import com.thoughtworks.go.plugin.api.request.DefaultGoApiRequest;
import com.thoughtworks.go.plugin.api.request.GoPluginApiRequest;
import com.thoughtworks.go.plugin.api.response.DefaultGoPluginApiResponse;
import com.thoughtworks.go.plugin.api.response.GoApiResponse;
import com.thoughtworks.go.plugin.api.response.GoPluginApiResponse;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * This class is the entry point into the Octane-GoCD-plugin.
 * The communication between plugin and GoCD server happens over a REST API.
 * Whenever the server wants to interact with this plugin it calls {@link #handle(GoPluginApiRequest)}.
 * @see <a href="https://plugin-api.gocd.org/current/notifications/#notification-plugins">Notification Plugins</a>
 */
@Extension
public class OctaneGoCDPlugin implements GoPlugin {

	/** This ID is referred to in the plugin.xml */
	public static String PluginID = "com.haufelexware.gocd.plugin.octane";

	private static final Logger Log = Logger.getLoggerFor(OctaneGoCDPlugin.class);

	private GoPluginServices goPluginServices = new GoPluginServices();

	@Override
	public void initializeGoApplicationAccessor(GoApplicationAccessor goApplicationAccessor) {
		{ // retrieve the current plugin settings from the server.
			DefaultGoApiRequest request = new DefaultGoApiRequest("go.processor.plugin-settings.get", "1.0", pluginIdentifier());
			request.setRequestBody(new Gson().toJson(Collections.singletonMap("plugin-id", PluginID)));
			GoApiResponse response = goApplicationAccessor.submit(request);
			if (response.responseCode() == 200) {
				OctaneGoCDPluginSettings settings = new Gson().fromJson(response.responseBody(), OctaneGoCDPluginSettings.class);
				goPluginServices.setSettings(settings != null ? settings : new OctaneGoCDPluginSettings());
			} else {
				goPluginServices.setSettings(new OctaneGoCDPluginSettings());
			}
		}
		{ // retrieve server info.
			DefaultGoApiRequest request = new DefaultGoApiRequest("go.processor.server-info.get", "1.0", pluginIdentifier());
			GoApiResponse response = goApplicationAccessor.submit(request);
			if (response.responseCode() == 200) {
				GoServerInfo serverInfo = new Gson().fromJson(response.responseBody(), GoServerInfo.class);
				String serverURL = serverInfo.getSecureSiteURL();
				if (serverURL == null || serverURL.isEmpty()) { // fall-back if there is no secure URL
					serverURL = serverInfo.getSiteURL();
				}
				if (serverURL == null || serverURL.isEmpty()) { // auto discover the site URL if there is still none.
					try {
						serverURL = "http://" + InetAddress.getLocalHost().getHostName() + ":8153/go";
					} catch (UnknownHostException e) {
						throw new IllegalArgumentException("Could not determine the serverURL. Please configure it in Go's Server Configuration.");
					}
				}
				goPluginServices.setGoServerURL(serverURL);
				Log.info("Go Server URL: " + goPluginServices.getGoServerURL());
				goPluginServices.setGoServerID(serverInfo.getServerID());
				Log.info("Go Server ID: " + goPluginServices.getGoServerID());
			}
		}
		OctaneSDK.init(goPluginServices, true);
		Log.info("HPE ALM Octane initialized with '" + goPluginServices.getSettings().getServerURL() + "'");
	}

	@Override
	public GoPluginApiResponse handle(GoPluginApiRequest request) throws UnhandledRequestTypeException {
		if ("stage-status".equals(request.requestName())) { // server is informing about a status change.
			GenericJsonObject statusInfo = new Gson().fromJson(request.requestBody(), GenericJsonObject.class);
			new OctaneCIEventBuilder(goPluginServices.createGoApiClient()).sendCIEvent(statusInfo);
			return new DefaultGoPluginApiResponse(200, new Gson().toJson(Collections.singletonMap("status", "success")));
		} else if ("go.plugin-settings.get-view".equals(request.requestName())) { // server is requesting the HTML template for this plugin's configuration.
			try {
				return new DefaultGoPluginApiResponse(200, new Gson().toJson(Collections.singletonMap("template", Streams.readAsString(getClass().getClassLoader().getResourceAsStream("settings.template.html")))));
			} catch (IOException e) {
				Log.error("could not load settings template", e);
			}
		} else if ("go.plugin-settings.get-configuration".equals(request.requestName())) { // server is requesting the possible configuration values.
			return new DefaultGoPluginApiResponse(200, new Gson().toJson(new MapBuilder<>(new HashMap<String,Object>())
				.put("serverURL", new MapBuilder<>(new HashMap<String,Object>())
					.put("display-name", "Server URL")
					.put("display-order", "0")
					.put("required", true)
					.build())
				.put("clientID", new MapBuilder<>(new HashMap<String,Object>())
					.put("display-name", "Client ID")
					.put("display-order", "1")
					.put("required", true)
					.build())
				.put("clientSecret", new MapBuilder<>(new HashMap<String,Object>())
					.put("display-name", "Client Secret")
					.put("display-order", "2")
					.put("required", true)
					.put("secure", true)
					.build())
				.put("goUsername", new MapBuilder<>(new HashMap<String,Object>())
					.put("display-name", "Go API Username")
					.put("display-order", "3")
					.put("required", true)
					.build())
				.put("goPassword", new MapBuilder<>(new HashMap<String,Object>())
					.put("display-name", "Go API Password")
					.put("display-order", "4")
					.put("required", true)
					.put("secure", true)
					.build())
				.build()));
		} else if ("go.plugin-settings.validate-configuration".equals(request.requestName())) { // server is asking for a validation of the given values.
			final OctaneGoCDPluginSettingsWrapper wrapper = new Gson().fromJson(request.requestBody(), OctaneGoCDPluginSettingsWrapper.class);
			final OctaneGoCDPluginSettings settings = wrapper.getPluginSettings();
			final List<ValidationIssue> issues = new SettingsValidator().validate(settings);
			if (issues.isEmpty()) { // test the connection if no validation issues have been found so far.
				((GoPluginServices)OctaneSDK.getInstance().getPluginServices()).setSettings(settings);
				try {
					final OctaneConfiguration config = OctaneSDK.getInstance().getPluginServices().getOctaneConfiguration();
					OctaneResponse response = OctaneSDK.getInstance().getConfigurationService().validateConfiguration(config);
					if (response.getStatus() == 401) { // authentication failed
						issues.add(new ValidationIssue("clientID", "Could not authenticate with Octane. Response: " + response.getStatus() + " " + response.getBody()));
					} else if (response.getStatus() != 200) {
						issues.add(new ValidationIssue("serverURL", "Could not connect to Octane. Response: " + response.getStatus() + " " + response.getBody()));
					}
					// if this point is reached the configuration seems valid. notify the SDK about the new config.
					OctaneSDK.getInstance().getConfigurationService().notifyChange();
				} catch (IllegalArgumentException|IOException e) {
					issues.add(new ValidationIssue("serverURL", "Could not connect to Octane. Exception thrown: " + e));
				}
			}
			return new DefaultGoPluginApiResponse(200, new Gson().toJson(issues));
		} else if ("notifications-interested-in".equals(request.requestName())) {
			return new DefaultGoPluginApiResponse(200, new Gson().toJson(Collections.singletonMap("notifications", Collections.singletonList("stage-status"))));
		}
		throw new UnhandledRequestTypeException(request.requestName());
	}

	@Override
	public GoPluginIdentifier pluginIdentifier() {
		return new GoPluginIdentifier("notification", Collections.singletonList("1.0"));
	}
}
