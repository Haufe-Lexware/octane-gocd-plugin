package com.haufelexware.gocd.dto;

import java.util.List;

/**
 * DTO for a pipeline in Go.
 */
public class GoPipeline {

	private String name;
	private String label;
	private List<GoStage> stages;
	private List<GoMaterial> materials;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public List<GoStage> getStages() {
		return stages;
	}

	public void setStages(List<GoStage> stages) {
		this.stages = stages;
	}

	public List<GoMaterial> getMaterials() {
		return materials;
	}

	public void setMaterials(List<GoMaterial> materials) {
		this.materials = materials;
	}
}
