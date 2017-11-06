package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This DTO represents a run of a single stage.
 */
public class GoStageInstance {

	private int id;
	private String name;
	@SerializedName("approved_by")
	private String approvedBy;
	@SerializedName("approval_type")
	private String approvalType;
	@SerializedName("can_run")
	private boolean canRun;
	private String result;
	private String counter;
	private List<GoJobInstance> jobs;

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

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	public String getApprovalType() {
		return approvalType;
	}

	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
	}

	public boolean isCanRun() {
		return canRun;
	}

	public void setCanRun(boolean canRun) {
		this.canRun = canRun;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getCounter() {
		return counter;
	}

	public void setCounter(String counter) {
		this.counter = counter;
	}

	public List<GoJobInstance> getJobs() {
		return jobs;
	}

	public void setJobs(List<GoJobInstance> jobs) {
		this.jobs = jobs;
	}

	public Long getFirstScheduledDate() {
		if (jobs != null && !jobs.isEmpty()) {
			return jobs.get(0).getScheduledDate();
		} else {
			return null;
		}
	}
}
