package com.haufelexware.gocd.dto;

import com.google.gson.annotations.SerializedName;

/**
 * DTO for a modification (aka commit to a repository).
 */
public class GoModification {

	private int id;
	@SerializedName("modified_time")
	private Long modifiedTime;
	@SerializedName("email_address")
	private String emailAddress;
	@SerializedName("user_name")
	private String userName;
	private String comment;
	private String revision;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Long getModifiedTime() {
		return modifiedTime;
	}

	public void setModifiedTime(Long modifiedTime) {
		this.modifiedTime = modifiedTime;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getRevision() {
		return revision;
	}

	public void setRevision(String revision) {
		this.revision = revision;
	}
}
