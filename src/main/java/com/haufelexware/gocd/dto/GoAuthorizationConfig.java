package com.haufelexware.gocd.dto;

import java.util.List;

/**
 * This DTO represents a authorization config.
 */
public class GoAuthorizationConfig {

	private List<String> users;
	private List<String> roles;

	public List<String> getUsers() {
		return users;
	}

	public void setUsers(List<String> users) {
		this.users = users;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
}
