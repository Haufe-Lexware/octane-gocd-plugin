package com.haufelexware.octane;

import com.haufelexware.gocd.dto.GoPipeline;
import com.haufelexware.gocd.dto.GoPipelineGroup;
import com.haufelexware.gocd.dto.GoStage;
import com.haufelexware.gocd.service.GoApiClient;
import com.haufelexware.gocd.service.GoGetPipelineGroups;
import com.haufelexware.gocd.plugin.octane.settings.OctaneGoCDPluginSettings;
import com.haufelexware.util.converter.Converter;
import com.haufelexware.util.converter.ListConverter;
import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.configuration.CIProxyConfiguration;
import com.hp.octane.integrations.dto.configuration.OctaneConfiguration;
import com.hp.octane.integrations.dto.general.CIJobsList;
import com.hp.octane.integrations.dto.general.CIPluginInfo;
import com.hp.octane.integrations.dto.general.CIServerInfo;
import com.hp.octane.integrations.dto.general.CIServerTypes;
import com.hp.octane.integrations.dto.pipelines.PipelineNode;
import com.hp.octane.integrations.dto.pipelines.PipelinePhase;
import com.hp.octane.integrations.spi.CIPluginServicesBase;
import com.thoughtworks.go.plugin.api.logging.Logger;

import java.net.*;
import java.util.ArrayList;
import java.util.List;

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

	public GoApiClient getGoApiClient() {
		try {
			return new GoApiClient(new URL(goServerURL), settings.getGoUsername(), settings.getGoPassword());
		} catch (MalformedURLException e) {
			throw new IllegalArgumentException("Could not parse the given serverURL '" + goServerURL + "'", e);
		}
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
		List<PipelineNode> pipelineNodes = new ArrayList<>();
		for (GoPipelineGroup group : new GoGetPipelineGroups(getGoApiClient()).get()) {
			for (GoPipeline pipeline : group.getPipelines()) {
				pipelineNodes.add(DTOFactory.getInstance().newDTO(PipelineNode.class)
					.setName(group.getName() + ":" + pipeline.getName()));
			}
		}
		return DTOFactory.getInstance().newDTO(CIJobsList.class)
			.setJobs(pipelineNodes.toArray(new PipelineNode[pipelineNodes.size()]));
	}

	@Override
	public PipelineNode getPipeline(final String rootCIJobId) {
		if (rootCIJobId == null || !rootCIJobId.contains(":")) {
			throw new IllegalArgumentException("given pipeline identifier is invalid '" + rootCIJobId + "'");
		}
		// search the config for this pipeline.
		for (GoPipelineGroup group : new GoGetPipelineGroups(getGoApiClient()).get()) {
			for (GoPipeline pipeline : group.getPipelines()) {
				if (rootCIJobId.equals(group.getName() + ":" + pipeline.getName())) {
					return DTOFactory.getInstance().newDTO(PipelineNode.class)
						.setJobCiId(rootCIJobId)
						.setName(pipeline.getName())
						.setPhasesInternal(ListConverter.convert(pipeline.getStages(), new Converter<GoStage, PipelinePhase>() {
							@Override
							public PipelinePhase convert(GoStage stage) {
								return DTOFactory.getInstance().newDTO(PipelinePhase.class)
									.setName(stage.getName());
							}
						}));
				}
			}
		}
		return null;
	}
}
