package com.haufelexware.gocd.dto;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

/**
 * This test ensures that paring a JSON into {@link GoPipelineConfig} is working correctly.
 */
public class GoPipelineConfigTest {

	@Test
	public void testParsingJsonIntoWrapper() {
		final String json = "{\n" +
			"  \"_links\": {\n" +
			"    \"self\": {\n" +
			"      \"href\": \"https://ci.example.com/go/api/admin/pipelines/my_pipeline\"\n" +
			"    },\n" +
			"    \"doc\": {\n" +
			"      \"href\": \"https://api.gocd.org/#pipeline-config\"\n" +
			"    },\n" +
			"    \"find\": {\n" +
			"      \"href\": \"https://ci.example.com/go/api/admin/pipelines/:name\"\n" +
			"    }\n" +
			"  },\n" +
			"  \"label_template\": \"${COUNT}\",\n" +
			"  \"enable_pipeline_locking\": false,\n" +
			"  \"name\": \"my_pipeline\",\n" +
			"  \"template\": null,\n" +
			"  \"origin\": {\n" +
			"    \"type\": \"local\",\n" +
			"    \"file\": \"cruise-config.xml\"\n" +
			"  },\n" +
			"  \"params\": [],\n" +
			"  \"environment_variables\": [],\n" +
			"  \"materials\": [\n" +
			"    {\n" +
			"      \"type\": \"git\",\n" +
			"      \"attributes\": {\n" +
			"        \"url\": \"git@github.com:example/sample_repo.git\",\n" +
			"        \"destination\": \"code\",\n" +
			"        \"filter\": {\n" +
			"            \"ignore\": [\n" +
			"                    \"**/*.*\",\n" +
			"                    \"**/*.html\"\n" +
			"            ]\n" +
			"        },\n" +
			"        \"invert_filter\": false,\n" +
			"        \"name\": \"git\",\n" +
			"        \"auto_update\": true,\n" +
			"        \"branch\": \"master\",\n" +
			"        \"submodule_folder\": null,\n" +
			"        \"shallow_clone\": true\n" +
			"      }\n" +
			"    }\n" +
			"  ],\n" +
			"  \"stages\": [\n" +
			"    {\n" +
			"      \"name\": \"my_stage\",\n" +
			"      \"fetch_materials\": true,\n" +
			"      \"clean_working_directory\": false,\n" +
			"      \"never_cleanup_artifacts\": false,\n" +
			"      \"approval\": {\n" +
			"        \"type\": \"success\",\n" +
			"        \"authorization\": {\n" +
			"          \"roles\": [],\n" +
			"          \"users\": []\n" +
			"        }\n" +
			"      },\n" +
			"      \"environment_variables\": [],\n" +
			"      \"jobs\": [\n" +
			"        {\n" +
			"          \"name\": \"my_job\",\n" +
			"          \"run_instance_count\": null,\n" +
			"          \"timeout\": 0,\n" +
			"          \"environment_variables\": [],\n" +
			"          \"resources\": [\n" +
			"                \"Linux\",\n" +
			"                \"Java\"\n" +
			"          ],\n" +
			"          \"tasks\": [\n" +
			"            {\n" +
			"              \"type\": \"exec\",\n" +
			"              \"attributes\": {\n" +
			"                \"run_if\": [\n" +
			"                  \"passed\"\n" +
			"                ],\n" +
			"                \"on_cancel\": {\n" +
			"                  \"type\": \"exec\",\n" +
			"                  \"attributes\": {\n" +
			"                    \"command\": \"ls\",\n" +
			"                    \"working_directory\": null\n" +
			"                  }\n" +
			"                },\n" +
			"                \"command\": \"sleep\",\n" +
			"                \"arguments\": [\n" +
			"                  \"10\"\n" +
			"                ],\n" +
			"                \"working_directory\": null\n" +
			"              }\n" +
			"            }\n" +
			"         ],\n" +
			"          \"tabs\": [\n" +
			"            {\n" +
			"              \"name\": \"cobertura\",\n" +
			"              \"path\": \"target/site/cobertura/*.xml\"\n" +
			"            }\n" +
			"          ],\n" +
			"          \"artifacts\": [\n" +
			"            {\n" +
			"              \"source\": \"target\",\n" +
			"              \"destination\": \"result\",\n" +
			"              \"type\": \"build\"\n" +
			"            },\n" +
			"            {\n" +
			"              \"source\": \"test\",\n" +
			"              \"destination\": \"res1\",\n" +
			"              \"type\": \"test\"\n" +
			"            }\n" +
			"          ],\n" +
			"          \"properties\": null\n" +
			"        }\n" +
			"      ]\n" +
			"    }\n" +
			"  ],\n" +
			"  \"tracking_tool\": null,\n" +
			"  \"timer\": null\n" +
			"}";

		final GoPipelineConfig config = new Gson().fromJson(json, GoPipelineConfig.class);
		Assert.assertNotNull("config should not be null", config);
		Assert.assertEquals("name should equal", "my_pipeline", config.getName());
		Assert.assertEquals("labelTemplate should equal", "${COUNT}", config.getLabelTemplate());
		Assert.assertEquals("enablePipelineLocking should equal", false, config.isEnablePipelineLocking());
	}
}
