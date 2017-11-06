package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This DTO represents the config of a job.
 */
public class GoJobConfig {

	private String name;
	@SerializedName("environment_variables")
	private List<GoEnvironmentVariable> environmentVariables;
	private List<String> resources;
	private List<GoTaskConfig> tasks;
	private List<GoTabConfig> tabs;
	private List<GoArtifactConfig> artifacts;
	private List<GoPropertyConfig> properties;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GoEnvironmentVariable> getEnvironmentVariables() {
		return environmentVariables;
	}

	public void setEnvironmentVariables(List<GoEnvironmentVariable> environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public List<String> getResources() {
		return resources;
	}

	public void setResources(List<String> resources) {
		this.resources = resources;
	}

	public List<GoTaskConfig> getTasks() {
		return tasks;
	}

	public void setTasks(List<GoTaskConfig> tasks) {
		this.tasks = tasks;
	}

	public List<GoTabConfig> getTabs() {
		return tabs;
	}

	public void setTabs(List<GoTabConfig> tabs) {
		this.tabs = tabs;
	}

	public List<GoArtifactConfig> getArtifacts() {
		return artifacts;
	}

	public void setArtifacts(List<GoArtifactConfig> artifacts) {
		this.artifacts = artifacts;
	}

	public List<GoPropertyConfig> getProperties() {
		return properties;
	}

	public void setProperties(List<GoPropertyConfig> properties) {
		this.properties = properties;
	}
}
