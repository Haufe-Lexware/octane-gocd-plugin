package com.haufelexware.gocd.dto;

import java.util.List;

/**
 * This DTO is for Go artifacts.
 */
public class GoArtifact {

	private String name;
	private String url;
	private String type; // one of "file" or "folder"
	private List<GoArtifact> files; // if type was folder

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<GoArtifact> getFiles() {
		return files;
	}

	public void setFiles(List<GoArtifact> files) {
		this.files = files;
	}
}
