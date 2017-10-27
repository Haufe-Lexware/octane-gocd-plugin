package com.haufelexware.gocd.plugin.octane.settings;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that parsing JSON into {@link OctaneGoCDPluginSettingsWrapper} works correctly.
 */
public class OctaneGoCDPluginSettingsWrapperTest {

	@Test
	public void testParsingJsonIntoWrapper() {
		final String json = "{ \"plugin-settings\": { " +
			" \"serverURL\"   : { \"value\": \"https://foobar.org\" }," +
			" \"clientID\"    : { \"value\": \"nobody\"             }," +
			" \"clientSecret\": { \"value\": \"password\"           }" +
			"}}";

		final OctaneGoCDPluginSettingsWrapper wrapper = new Gson().fromJson(json, OctaneGoCDPluginSettingsWrapper.class);
		Assert.assertNotNull("wrapper should not be null", wrapper);
		final OctaneGoCDPluginSettings settings = wrapper.getPluginSettings();
		Assert.assertNotNull("wrapper should contain settings", settings);
		Assert.assertEquals("serverURL should equal", "https://foobar.org", settings.getServerURL());
		Assert.assertEquals("clientID should equal", "nobody", settings.getClientID());
		Assert.assertEquals("clientSecret should equal", "password", settings.getClientSecret());
	}
}
