package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This DTO represents a single pipeline configuration in Go.
 */
public class GoPipelineConfig {

	private String name;
	@SerializedName("label_template")
	private String labelTemplate;
	@SerializedName("enablePipelineLocking")
	private boolean enablePipelineLocking;
	private String template;
	private List<GoParameter> parameters;
	@SerializedName("environment_variables")
	private List<GoEnvironmentVariable> environmentVariables;
	private List<GoMaterialConfig> materials;
	private List<GoStageConfig> stages;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabelTemplate() {
		return labelTemplate;
	}

	public void setLabelTemplate(String labelTemplate) {
		this.labelTemplate = labelTemplate;
	}

	public boolean isEnablePipelineLocking() {
		return enablePipelineLocking;
	}

	public void setEnablePipelineLocking(boolean enablePipelineLocking) {
		this.enablePipelineLocking = enablePipelineLocking;
	}

	public String getTemplate() {
		return template;
	}

	public void setTemplate(String template) {
		this.template = template;
	}

	public List<GoParameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<GoParameter> parameters) {
		this.parameters = parameters;
	}

	public List<GoEnvironmentVariable> getEnvironmentVariables() {
		return environmentVariables;
	}

	public void setEnvironmentVariables(List<GoEnvironmentVariable> environmentVariables) {
		this.environmentVariables = environmentVariables;
	}

	public List<GoMaterialConfig> getMaterials() {
		return materials;
	}

	public void setMaterials(List<GoMaterialConfig> materials) {
		this.materials = materials;
	}

	public List<GoStageConfig> getStages() {
		return stages;
	}

	public void setStages(List<GoStageConfig> stages) {
		this.stages = stages;
	}
}
