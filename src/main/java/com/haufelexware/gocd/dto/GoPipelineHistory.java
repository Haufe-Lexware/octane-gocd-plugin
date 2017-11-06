package com.haufelexware.gocd.dto;

import java.util.List;

/**
 * This DTO is the response of {@link com.haufelexware.gocd.service.GoGetPipelineHistory}.
 */
public class GoPipelineHistory {

	private List<GoPipelineInstance> pipelines;

	public List<GoPipelineInstance> getPipelines() {
		return pipelines;
	}

	public void setPipelines(List<GoPipelineInstance> pipelines) {
		this.pipelines = pipelines;
	}
}
