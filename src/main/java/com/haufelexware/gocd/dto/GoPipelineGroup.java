package com.haufelexware.gocd.dto;

import java.util.List;

/**
 * DTO for rendering a pipeline group.
 */
public class GoPipelineGroup {

	private String name;
	private List<GoPipeline> pipelines;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<GoPipeline> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<GoPipeline> pipelines) {
		this.pipelines = pipelines;
	}
}
