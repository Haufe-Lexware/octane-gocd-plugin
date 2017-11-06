package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

/**
 * This DTO represents a single state transition.
 */
public class GoJobStateTransition {

	private String id;
	private String state;
	@SerializedName("state_change_time")
	private long stateChangeTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public long getStateChangeTime() {
		return stateChangeTime;
	}

	public void setStateChangeTime(long stateChangeTime) {
		this.stateChangeTime = stateChangeTime;
	}
}
