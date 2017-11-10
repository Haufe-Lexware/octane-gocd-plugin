package com.haufelexware.gocd.dto;

import java.util.List;

/**
 * DTO for a material revision in a pipeline.
 */
public class GoMaterialRevision {

	private GoMaterial material;
	private boolean changed;
	private List<GoModification> modifications;

	public GoMaterial getMaterial() {
		return material;
	}

	public void setMaterial(GoMaterial material) {
		this.material = material;
	}

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public List<GoModification> getModifications() {
		return modifications;
	}

	public void setModifications(List<GoModification> modifications) {
		this.modifications = modifications;
	}
}
