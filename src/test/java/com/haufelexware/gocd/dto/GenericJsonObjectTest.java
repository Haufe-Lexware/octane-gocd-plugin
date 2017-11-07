package com.haufelexware.gocd.dto;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensure that {@link GenericJsonObject} works correctly.
 */
public class GenericJsonObjectTest {

	@Test
	public void testParsingStageStartNotification() {
		final String json = "{\n" +
			"  \"pipeline\": {\n" +
			"    \"name\": \"pipeline-name\",\n" +
			"    \"counter\": \"9\",\n" +
			"    \"group\": \"defaultGroup\",\n" +
			"    \"build-cause\": [\n" +
			"      {\n" +
			"        \"material\": {\n" +
			"          \"git-configuration\": {\n" +
			"            \"shallow-clone\": false,\n" +
			"            \"branch\": \"2.x\",\n" +
			"            \"url\": \"https://github.com/organization/repository\"\n" +
			"          },\n" +
			"          \"type\": \"git\"\n" +
			"        },\n" +
			"        \"changed\": false,\n" +
			"        \"modifications\": [\n" +
			"          {\n" +
			"            \"revision\": \"8f60b12439840e5a0a4d464379dd3a48881008b4\",\n" +
			"            \"modified-time\": \"2017-03-23T17:27:58.000Z\",\n" +
			"            \"data\": {}\n" +
			"          }\n" +
			"        ]\n" +
			"      }\n" +
			"    ],\n" +
			"    \"stage\": {\n" +
			"      \"name\": \"stageOne\",\n" +
			"      \"counter\": \"1\",\n" +
			"      \"approval-type\": \"success\",\n" +
			"      \"approved-by\": \"timer\",\n" +
			"      \"state\": \"Building\",\n" +
			"      \"result\": \"Unknown\",\n" +
			"      \"create-time\": \"2017-03-23T20:44:02.119Z\",\n" +
			"      \"jobs\": [\n" +
			"        {\n" +
			"          \"name\": \"job1\",\n" +
			"          \"schedule-time\": \"2017-03-23T20:44:02.119Z\",\n" +
			"          \"state\": \"Scheduled\",\n" +
			"          \"result\": \"Unknown\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"name\": \"job2\",\n" +
			"          \"schedule-time\": \"2017-03-23T20:44:02.119Z\",\n" +
			"          \"state\": \"Scheduled\",\n" +
			"          \"result\": \"Unknown\"\n" +
			"        },\n" +
			"        {\n" +
			"          \"name\": \"job3\",\n" +
			"          \"schedule-time\": \"2017-03-23T20:44:02.119Z\",\n" +
			"          \"state\": \"Scheduled\",\n" +
			"          \"result\": \"Unknown\"\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  }\n" +
			"}";

		GenericJsonObject object = new Gson().fromJson(json, GenericJsonObject.class);
		Assert.assertNotNull("generic JSON object should not be null", object);
		Assert.assertEquals("pipeline name", "pipeline-name", object.getValue("pipeline", "name"));
		Assert.assertEquals("stage state", "Building", object.getValue("pipeline", "stage", "state"));
	}
}
