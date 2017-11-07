package com.haufelexware.gocd.plugin.octane;

import com.haufelexware.gocd.dto.GenericJsonObject;
import com.haufelexware.gocd.dto.GoPipelineConfig;
import com.haufelexware.gocd.dto.GoPipelineInstance;
import com.haufelexware.gocd.dto.GoStageConfig;
import com.haufelexware.gocd.service.GoApiClient;
import com.haufelexware.gocd.service.GoGetPipelineConfig;
import com.haufelexware.gocd.service.GoGetPipelineInstance;
import com.hp.octane.integrations.OctaneSDK;
import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.causes.CIEventCause;
import com.hp.octane.integrations.dto.events.CIEvent;
import com.hp.octane.integrations.dto.events.CIEventType;
import com.hp.octane.integrations.dto.snapshots.CIBuildResult;
import com.thoughtworks.go.plugin.api.logging.Logger;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * This build helps converting GoCD status information into {@link CIEvent}s Octane can understand.
 *
 * GoCD uses a stage-centric approach, meaning it will send state event, whenever a stage is about
 * to be build or finishes building. Octane on the other hand is only interested in the overall
 * pipeline and does not care about the stages in detail.
 *
 * @see <a href="https://plugin-api.gocd.org/current/notifications/#stage-start-notifications">Stage Start Notifications</a>
 * @see <a href="https://plugin-api.gocd.org/current/notifications/#stage-completion-notifications">Stage Completion Notifications</a>
 */
public class OctaneCIEventBuilder {

	/** All possible values for a state of a stage in a pipeline. */
	public enum PipelineStageState {
		Building,
		Passed,
		Failed,
		Cancelled
	}

	protected static final Logger Log = Logger.getLoggerFor(OctaneCIEventBuilder.class);

	private final GoApiClient goApiClient;

	public OctaneCIEventBuilder(GoApiClient goApiClient) {
		this.goApiClient = goApiClient;
	}

	/**
	 * This method will analyze the given {@param statusInfo} and may or may not generate
	 * and send a {@link CIEvent} to Octane.
	 */
	public void sendCIEvent(GenericJsonObject statusInfo) {
		if (statusInfo == null) {
			return;
		}

		final String pipelineName = String.valueOf(statusInfo.getValue("pipeline", "name"));
		final GoPipelineConfig pipelineConfig = new GoGetPipelineConfig(goApiClient).get(pipelineName);
		final List<GoStageConfig> stages = pipelineConfig.getStages();

		if (PipelineStageState.Building.name().equals(statusInfo.getValue("pipeline", "stage", "state"))) {
			// only generate a start-event if the current stage is the very first one in the pipeline.
			if (stages != null && !stages.isEmpty() &&
				stages.get(0).getName().equals(String.valueOf(statusInfo.getValue("pipeline", "stage", "name")))) {
				sendStartEvent(statusInfo);
			}
		} else if (PipelineStageState.Passed.name().equals(statusInfo.getValue("pipeline", "stage", "state"))) {
			// whenever a stage passes check whether it is the last stage of this pipeline; only then send an end-event.
			if (stages != null && !stages.isEmpty() &&
				stages.get(stages.size() - 1).getName().equals(String.valueOf(statusInfo.getValue("pipeline", "stage", "name")))) {
				sendEndEvent(statusInfo);
			}
		} else {
			// all other state changes end a pipeline build.
			sendEndEvent(statusInfo);
		}
	}

	private void sendStartEvent(GenericJsonObject statusInfo) {
		CIEvent event = DTOFactory.getInstance().newDTO(CIEvent.class)
			.setEventType(CIEventType.STARTED)
			.setProject(String.valueOf(statusInfo.getValue("pipeline", "name")))
			.setProjectDisplayName(String.valueOf(statusInfo.getValue("pipeline", "name")))
			.setBuildCiId(String.valueOf(statusInfo.getValue("pipeline", "counter")))
			.setNumber(String.valueOf(statusInfo.getValue("pipeline", "counter")))
			.setCauses(Collections.<CIEventCause>emptyList());

		Date createTime = parseTime(String.valueOf(statusInfo.getValue("pipeline", "stage", "create-time")));
		if (createTime != null) {
			event.setStartTime(createTime.getTime());
		}

		OctaneSDK.getInstance().getEventsService().publishEvent(event);
	}

	private void sendEndEvent(GenericJsonObject statusInfo) {
		final String pipelineName = String.valueOf(statusInfo.getValue("pipeline", "name"));
		final String pipelineCounter = String.valueOf(statusInfo.getValue("pipeline", "counter"));
		CIEvent event = DTOFactory.getInstance().newDTO(CIEvent.class)
			.setEventType(CIEventType.FINISHED)
			.setProject(pipelineName)
			.setProjectDisplayName(pipelineName)
			.setBuildCiId(pipelineCounter)
			.setNumber(pipelineCounter)
			.setCauses(Collections.<CIEventCause>emptyList());

		PipelineStageState stageState = PipelineStageState.valueOf(String.valueOf(statusInfo.getValue("pipeline", "stage", "state")));
		switch (stageState) {
			case Passed: event.setResult(CIBuildResult.SUCCESS); break;
			case Failed: event.setResult(CIBuildResult.FAILURE); break;
			case Cancelled: event.setResult(CIBuildResult.ABORTED); break;
		}

		// determine the start-time of this pipeline.
		GoPipelineInstance pipelineInstance = new GoGetPipelineInstance(goApiClient).get(pipelineName, Integer.valueOf(pipelineCounter));
		Date lastTransitionTime = parseTime(String.valueOf(statusInfo.getValue("pipeline", "stage", "last-transition-time")));
		if (lastTransitionTime != null && pipelineInstance != null && pipelineInstance.getFirstScheduledDate() != null) {
			event.setDuration(lastTransitionTime.getTime() - pipelineInstance.getFirstScheduledDate()); // in ms
		}

		OctaneSDK.getInstance().getEventsService().publishEvent(event);
	}

	/**
	 * This helper method help parsing a given time into a {@link Date}.
	 */
	public static Date parseTime(String time) {
		if (time == null) {
			return null;
		}
		/* Even though the time looks like being a Zulu-time it probably isn't.
		 * Please see whether bug https://github.com/gocd/gocd/issues/3989 is still open.
		 * The StageConverter used to render the time has probably used the default local to
		 * render the time and did just append the letter 'Z'. */
		try { // try to parse the time with an RFC822 timezone
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").parse(time);
		} catch (ParseException e) {
			Log.warn("Could not parse given time with RFC822 timezone '" + time + "'");
		}
		try { // try to parse the time with the pattern the StageConverter was probably using
			return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(time);
		} catch (ParseException e) {
			Log.error("Could not parse given time with the assumed pattern", e);
		}
		return null; // giving up
	}
}
