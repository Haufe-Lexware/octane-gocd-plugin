package com.haufelexware.octane;

import com.haufelexware.gocd.plugin.octane.settings.OctaneGoCDPluginSettings;
import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.configuration.CIProxyConfiguration;
import com.hp.octane.integrations.dto.configuration.OctaneConfiguration;
import com.hp.octane.integrations.dto.general.CIJobsList;
import com.hp.octane.integrations.dto.general.CIPluginInfo;
import com.hp.octane.integrations.dto.general.CIServerInfo;
import com.hp.octane.integrations.dto.general.CIServerTypes;
import com.hp.octane.integrations.spi.CIPluginServicesBase;
import com.thoughtworks.go.plugin.api.logging.Logger;

import java.net.*;

/**
 * This class is the entry point into the Octane-PluginService.
 * As described in <a href="https://github.com/HPSoftware/octane-ci-java-sdk/blob/master/README.md">ReadMe</a>
 * it derives from {@link CIPluginServicesBase}.
 */
public class GoPluginServices extends CIPluginServicesBase {

	private static final Logger Log = Logger.getLoggerFor(GoPluginServices.class);

	private OctaneGoCDPluginSettings settings;
	private String goServerID;
	private String goServerURL;

	public OctaneGoCDPluginSettings getSettings() {
		return settings;
	}

	public void setSettings(OctaneGoCDPluginSettings settings) {
		this.settings = settings;
	}

	public String getGoServerID() {
		return goServerID;
	}

	public void setGoServerID(String goServerID) {
		this.goServerID = goServerID;
	}

	public String getGoServerURL() {
		return goServerURL;
	}

	public void setGoServerURL(String goServerURL) {
		this.goServerURL = goServerURL;
	}

	@Override
	public CIPluginInfo getPluginInfo() {
		return DTOFactory.getInstance().newDTO(CIPluginInfo.class).setVersion("1.0");
	}

	@Override
	public CIServerInfo getServerInfo() {
		return DTOFactory.getInstance().newDTO(CIServerInfo.class)
			.setUrl(goServerURL)
			.setType(CIServerTypes.UNKNOWN) // TODO change into CIServerTypes.GoCD as soon as available.
			.setSendingTime(System.currentTimeMillis())
			.setInstanceId(goServerID);
	}

	@Override
	public OctaneConfiguration getOctaneConfiguration() {
		int contextPathPosition = settings.getServerURL().indexOf("/ui");
		if (contextPathPosition < 0) {
			throw new IllegalArgumentException("URL does not conform to the expected format");
		}
		final String baseURL = settings.getServerURL().substring(0, contextPathPosition);

		final URL url;
		try {
			url = new URL(settings.getServerURL());
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Could not parse serverURL '" + settings.getServerURL() + "'");
		}

		// find the id of the shared space.
		for (String param : url.getQuery().split("&")) {
			if (param.startsWith("p=")) {
				String[] spaces = param.substring(2).split("/");
				if (spaces.length < 1 || spaces[0].isEmpty()) {
					throw new IllegalArgumentException("sharedSpace ID must be present value of parameter p");
				}
				return DTOFactory.getInstance().newDTO(OctaneConfiguration.class)
					.setUrl(baseURL)
					.setSharedSpace(spaces[0])
					.setApiKey(settings.getClientID())
					.setSecret(settings.getClientSecret());
			}
		}
		throw new IllegalArgumentException("URL must contain parameter p with IDs for sharedSpace and workspace");
	}

	@Override
	public CIProxyConfiguration getProxyConfiguration(final String targetHost) {
		Log.info("proxy configuration requested for host '" + targetHost + "'");
		try {
			final String protocol = new URL(targetHost).getProtocol();
			if (System.getProperty(protocol + ".proxyHost") != null) { // is a proxy defined for this protocol?
				Log.info("using proxy " + System.getProperty(protocol + ".proxyHost"));
				return DTOFactory.getInstance().newDTO(CIProxyConfiguration.class)
					.setHost(System.getProperty(protocol + ".proxyHost"))
					.setPort(Integer.valueOf(System.getProperty(protocol + ".proxyPort")))
					.setUsername(System.getProperty(protocol + ".proxyUser"))
					.setPassword(System.getProperty(protocol + ".proxyPassword"));
			} else {
				Log.info("using no proxy");
			}
		} catch (MalformedURLException e) {
			Log.error("Could not parse given targetHost as URL. Proceeding with using no proxy configuration.");
		}
		return null; // use no proxy
	}

	@Override
	public CIJobsList getJobsList(boolean includeParameters) {
		return DTOFactory.getInstance().newDTO(CIJobsList.class);
	}
}
