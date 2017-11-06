package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A pipeline instance documents the run of a pipeline.
 */
public class GoPipelineInstance {

	private String id;
	private String name;
	private String label;
	private int counter;
	@SerializedName("natural_order")
	private int naturalOrder;
	@SerializedName("can_run")
	private boolean canRun;
	private String comment;
	@SerializedName("build_cause")
	private GoBuildCause buildCause;
	private List<GoStageInstance> stages;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}

	public int getNaturalOrder() {
		return naturalOrder;
	}

	public void setNaturalOrder(int naturalOrder) {
		this.naturalOrder = naturalOrder;
	}

	public boolean isCanRun() {
		return canRun;
	}

	public void setCanRun(boolean canRun) {
		this.canRun = canRun;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public GoBuildCause getBuildCause() {
		return buildCause;
	}

	public void setBuildCause(GoBuildCause buildCause) {
		this.buildCause = buildCause;
	}

	public List<GoStageInstance> getStages() {
		return stages;
	}

	public void setStages(List<GoStageInstance> stages) {
		this.stages = stages;
	}

	public Long getFirstScheduledDate() {
		if (stages != null && !stages.isEmpty()) {
			return stages.get(0).getFirstScheduledDate();
		} else {
			return null;
		}
	}

	public Long getLastJobTransitionDate() {
		if (stages != null && !stages.isEmpty()) {
			return stages.get(stages.size() - 1).getLastJobTransitionDate();
		} else {
			return null;
		}
	}

	public Long getDuration() {
		Long scheduledDate = getFirstScheduledDate();
		Long lastTransitionDate = getLastJobTransitionDate();
		if (scheduledDate != null && lastTransitionDate != null) {
			return lastTransitionDate - scheduledDate;
		} else {
			return null;
		}
	}
}
