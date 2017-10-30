package com.haufelexware.gocd.dto;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensure that {@link GoPipelineGroup} can be parsed correctly.
 */
public class GoPipelineGroupTest {

	@Test
	public void testParsingJsonIntoWrapper() {
		final String json = "[\n" +
			"  {\n" +
			"    \"pipelines\": [\n" +
			"      {\n" +
			"        \"stages\": [\n" +
			"          {\n" +
			"            \"name\": \"up42_stage\"\n" +
			"          }\n" +
			"        ],\n" +
			"        \"name\": \"up42\",\n" +
			"        \"materials\": [\n" +
			"          {\n" +
			"            \"description\": \"URL: https://github.com/gocd/gocd, Branch: master\",\n" +
			"            \"fingerprint\": \"2d05446cd52a998fe3afd840fc2c46b7c7e421051f0209c7f619c95bedc28b88\",\n" +
			"            \"type\": \"Git\"\n" +
			"          }\n" +
			"        ],\n" +
			"        \"label\": \"${COUNT}\"\n" +
			"      }\n" +
			"    ],\n" +
			"    \"name\": \"first\"\n" +
			"  }\n" +
			"]";

		final List<GoPipelineGroup> groups = new Gson().fromJson(json, GoPipelineGroups.class);
		Assert.assertNotNull("groups should not be null", groups);
		Assert.assertEquals("groups should contain one group", 1, groups.size());
		GoPipelineGroup group = groups.get(0);
		Assert.assertEquals("name of pipeline group", "first", group.getName());
		Assert.assertEquals("number of pipelines in group", 1, group.getPipelines().size());
		GoPipeline pipeline = group.getPipelines().get(0);
		Assert.assertEquals("name of pipeline", "up42", pipeline.getName());
		Assert.assertEquals("label of pipeline", "${COUNT}", pipeline.getLabel());
		Assert.assertEquals("number of stages in pipeline", 1, pipeline.getStages().size());
		Assert.assertEquals("number of materials in pipeline", 1, pipeline.getMaterials().size());
		Assert.assertEquals("name of stage", "up42_stage", pipeline.getStages().get(0).getName());
		GoMaterial material = pipeline.getMaterials().get(0);
		Assert.assertEquals("type of material", "Git", material.getType());
		Assert.assertEquals("description of material", "URL: https://github.com/gocd/gocd, Branch: master", material.getDescription());
		Assert.assertEquals("fingerprint of material", "2d05446cd52a998fe3afd840fc2c46b7c7e421051f0209c7f619c95bedc28b88", material.getFingerprint());
	}
}
