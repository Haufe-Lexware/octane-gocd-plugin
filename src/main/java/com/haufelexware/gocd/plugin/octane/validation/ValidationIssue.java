package com.haufelexware.gocd.plugin.octane.validation;

/**
 * This POJO carries a single detected validation issue.
 */
public class ValidationIssue {

	private String key;
	private String message;

	public ValidationIssue(String key, String message) {
		this.key = key;
		this.message = message;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		ValidationIssue that = (ValidationIssue) o;

		if (key != null ? !key.equals(that.key) : that.key != null) {
			return false;
		}
		return message != null ? message.equals(that.message) : that.message == null;

	}

	@Override
	public int hashCode() {
		int result = key != null ? key.hashCode() : 0;
		result = 31 * result + (message != null ? message.hashCode() : 0);
		return result;
	}
}
