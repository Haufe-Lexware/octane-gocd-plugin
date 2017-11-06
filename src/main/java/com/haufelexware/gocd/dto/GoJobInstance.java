package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This DTO is an instance of a job.
 */
public class GoJobInstance {

	private int id;
	private String name;
	@SerializedName("agent_uuid")
	private String agentUuid;
	@SerializedName("scheduled_date")
	private long scheduledDate;
	private String result;
	private String state;
	@SerializedName("job_state_transition")
	private List<GoJobStateTransition> jobStateTransitions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAgentUuid() {
		return agentUuid;
	}

	public void setAgentUuid(String agentUuid) {
		this.agentUuid = agentUuid;
	}

	public long getScheduledDate() {
		return scheduledDate;
	}

	public void setScheduledDate(long scheduledDate) {
		this.scheduledDate = scheduledDate;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<GoJobStateTransition> getJobStateTransitions() {
		return jobStateTransitions;
	}

	public void setJobStateTransitions(List<GoJobStateTransition> jobStateTransitions) {
		this.jobStateTransitions = jobStateTransitions;
	}

	public Long getLastJobTransitionDate() {
		if (jobStateTransitions != null && !jobStateTransitions.isEmpty()) {
			return jobStateTransitions.get(jobStateTransitions.size() - 1).getStateChangeTime();
		} else {
			return null;
		}
	}

	public Long getDuration() {
		Long lastTransitionDate = getLastJobTransitionDate();
		if (lastTransitionDate != null) {
			return lastTransitionDate - scheduledDate;
		} else {
			return null;
		}
	}
}
