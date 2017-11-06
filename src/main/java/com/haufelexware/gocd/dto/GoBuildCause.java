package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

/**
 * This DTO represent the cause of a build.
 */
public class GoBuildCause {

	private String approver;
	@SerializedName("trigger_forced")
	private boolean triggerForced;
	@SerializedName("trigger_message")
	private String triggerMessage;

	public String getApprover() {
		return approver;
	}

	public void setApprover(String approver) {
		this.approver = approver;
	}

	public boolean isTriggerForced() {
		return triggerForced;
	}

	public void setTriggerForced(boolean triggerForced) {
		this.triggerForced = triggerForced;
	}

	public String getTriggerMessage() {
		return triggerMessage;
	}

	public void setTriggerMessage(String triggerMessage) {
		this.triggerMessage = triggerMessage;
	}
}
