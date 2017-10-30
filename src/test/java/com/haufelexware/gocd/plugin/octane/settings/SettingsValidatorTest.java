package com.haufelexware.gocd.plugin.octane.settings;

import com.haufelexware.gocd.plugin.octane.validation.ValidationIssue;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that {@link SettingsValidator} is working correctly.
 */
public class SettingsValidatorTest {

	@Test(expected = IllegalArgumentException.class)
	public void testAgainstNullSettings() {
		new SettingsValidator().validate(null);
	}

	@Test
	public void testCorrectSettings() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setServerURL("https://foobar.org/wildpath?token=s")
			.setClientID("nobody")
			.setClientSecret("key")
			.setGoUsername("alice")
			.setGoPassword("42"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertTrue("list of issues should be empty", issues.isEmpty());
	}

	@Test
	public void testAgainstSettingsWithMissingServerURL() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setClientID("nobody")
			.setClientSecret("key"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for missing serverURL", issues.contains(new ValidationIssue("serverURL", "Server URL can not be empty")));
	}

	@Test
	public void testAgainstSettingsWithMalformedServerURL() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setServerURL("://forbar.org")
			.setClientID("nobody")
			.setClientSecret("key"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for malformed serverURL", issues.contains(new ValidationIssue("serverURL", "Server URL is malformed")));
	}

	@Test
	public void testAgainstSettingsWithMissingClientID() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setServerURL("https://forbar.org")
			.setClientSecret("key"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for missing clientID", issues.contains(new ValidationIssue("clientID", "Client ID can not be empty")));
	}

	@Test
	public void testAgainstSettingsWithMissingClientSecret() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setServerURL("https://forbar.org")
			.setClientID("nobody"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for missing clientSecret", issues.contains(new ValidationIssue("clientSecret", "Client Secret can not be empty")));
	}

	@Test
	public void testAgainstSettingsWithMissingGoUsername() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setServerURL("https://forbar.org")
			.setClientID("nobody")
			.setClientSecret("key")
			.setGoPassword("57"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for missing clientSecret", issues.contains(new ValidationIssue("goUsername", "Go API Username can not be empty")));
	}

	@Test
	public void testAgainstSettingsWithMissingGoPassword() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings()
			.setServerURL("https://forbar.org")
			.setClientID("nobody")
			.setClientSecret("key")
			.setGoUsername("alice"));
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for missing clientSecret", issues.contains(new ValidationIssue("goPassword", "Go API Password can not be empty")));
	}

	@Test
	public void testAgainstEmptySettings() {
		final List<ValidationIssue> issues = new SettingsValidator().validate(new OctaneGoCDPluginSettings());
		Assert.assertNotNull("list of issues should not be null", issues);
		Assert.assertFalse("list of issues should not be empty", issues.isEmpty());
		Assert.assertTrue("list should contain validation issue for missing serverURL", issues.contains(new ValidationIssue("serverURL", "Server URL can not be empty")));
		Assert.assertTrue("list should contain validation issue for missing clientID", issues.contains(new ValidationIssue("clientID", "Client ID can not be empty")));
		Assert.assertTrue("list should contain validation issue for missing clientSecret", issues.contains(new ValidationIssue("clientSecret", "Client Secret can not be empty")));
	}
}
