package com.haufelexware.gocd.plugin.octane.converter;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * This test ensures that {@link OctaneCIEventBuilder} is working correctly.
 */
public class OctaneCIEventBuilderTest {

	@Test
	public void testParsingFakeZuluDate() {
		Date date = OctaneCIEventBuilder.parseTime("2017-11-07T14:26:08.720Z");
		Assert.assertNotNull("date should not be null", date);
	}

	@Test
	public void testParsingRFC822TimezoneDate() {
		Date date = OctaneCIEventBuilder.parseTime("2017-11-07T14:26:08.720+0100");
		Assert.assertNotNull("date should not be null", date);
	}
}
