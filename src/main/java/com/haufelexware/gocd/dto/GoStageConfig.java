package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This DTO represents a stage config.
 */
public class GoStageConfig {

	private String name;
	@SerializedName("fetch_materials")
	private boolean fetchMaterials;
	@SerializedName("clean_working_directory")
	private boolean cleanWorkingDirectory;
	@SerializedName("never_cleanup_artifacts")
	private boolean neverCleanupArtifacts;
	private GoApprovalConfig approval;
	@SerializedName("environment_variables")
	private List<GoEnvironmentVariable> environmentVariables;
	private List<GoJobConfig> jobs;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isFetchMaterials() {
		return fetchMaterials;
	}

	public void setFetchMaterials(boolean fetchMaterials) {
		this.fetchMaterials = fetchMaterials;
	}

	public boolean isCleanWorkingDirectory() {
		return cleanWorkingDirectory;
	}

	public void setCleanWorkingDirectory(boolean cleanWorkingDirectory) {
		this.cleanWorkingDirectory = cleanWorkingDirectory;
	}

	public boolean isNeverCleanupArtifacts() {
		return neverCleanupArtifacts;
	}

	public void setNeverCleanupArtifacts(boolean neverCleanupArtifacts) {
		this.neverCleanupArtifacts = neverCleanupArtifacts;
	}

	public GoApprovalConfig getApproval() {
		return approval;
	}

	public void setApproval(GoApprovalConfig approval) {
		this.approval = approval;
	}

	public List<GoEnvironmentVariable> getEnvironmentVariables() {
		return environmentVariables;
	}

	public void setEnvironmentVariables(List<GoEnvironmentVariable> environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public List<GoJobConfig> getJobs() {
		return jobs;
	}

	public void setJobs(List<GoJobConfig> jobs) {
		this.jobs = jobs;
	}
}
