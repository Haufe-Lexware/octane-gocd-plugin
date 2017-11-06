package com.haufelexware.gocd.dto;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that paring a JSON into {@link GoPipelineConfig} is working correctly.
 */
public class GoPipelineHistoryTest {

	@Test
	public void testParsingJsonIntoWrapper() {
		final String json = "{\n" +
			"  \"pipelines\": [\n" +
			"    {\n" +
			"      \"build_cause\": {\n" +
			"        \"approver\": \"anonymous\",\n" +
			"        \"material_revisions\": [\n" +
			"          {\n" +
			"            \"modifications\": [\n" +
			"              {\n" +
			"                \"email_address\": null,\n" +
			"                \"id\": 1,\n" +
			"                \"modified_time\": 1434957613000,\n" +
			"                \"user_name\": \"Pick E Reader <pick.e.reader@example.com>\",\n" +
			"                \"comment\": \"my hola mundo changes\",\n" +
			"                \"revision\": \"c194b49db102b705ebc13e604e490ae13ac92d96\"\n" +
			"              }\n" +
			"            ],\n" +
			"            \"material\": {\n" +
			"              \"description\": \"URL: https://github.com/gocd/gocd, Branch: master\",\n" +
			"              \"fingerprint\": \"f6e7a3899c55e1682ffb00383bdf8f882bcee2141e79a8728254190a1fddcf4f\",\n" +
			"              \"type\": \"Git\",\n" +
			"              \"id\": 1\n" +
			"            },\n" +
			"            \"changed\": false\n" +
			"          }\n" +
			"        ],\n" +
			"        \"trigger_forced\": true,\n" +
			"        \"trigger_message\": \"Forced by anonymous\"\n" +
			"      },\n" +
			"      \"name\": \"pipeline1\",\n" +
			"      \"natural_order\": 11,\n" +
			"      \"can_run\": true,\n" +
			"      \"comment\": null,\n" +
			"      \"stages\": [\n" +
			"        {\n" +
			"          \"name\": \"stage1\",\n" +
			"          \"approved_by\": \"admin\",\n" +
			"          \"jobs\": [\n" +
			"            {\n" +
			"              \"name\": \"job1\",\n" +
			"              \"result\": \"Failed\",\n" +
			"              \"state\": \"Completed\",\n" +
			"              \"id\": 13,\n" +
			"              \"scheduled_date\": 1436172201081\n" +
			"            }\n" +
			"          ],\n" +
			"          \"can_run\": true,\n" +
			"          \"result\": \"Failed\",\n" +
			"          \"approval_type\": \"success\",\n" +
			"          \"counter\": \"1\",\n" +
			"          \"id\": 13,\n" +
			"          \"operate_permission\": true,\n" +
			"          \"rerun_of_counter\": null,\n" +
			"          \"scheduled\": true\n" +
			"        }\n" +
			"      ],\n" +
			"      \"counter\": 11,\n" +
			"      \"id\": 13,\n" +
			"      \"preparing_to_schedule\": false,\n" +
			"      \"label\": \"11\"\n" +
			"    },\n" +
			"    {\n" +
			"      \"build_cause\": {\n" +
			"        \"approver\": \"anonymous\",\n" +
			"        \"material_revisions\": [\n" +
			"          {\n" +
			"            \"modifications\": [\n" +
			"              {\n" +
			"                \"email_address\": null,\n" +
			"                \"id\": 1,\n" +
			"                \"modified_time\": 1434957613000,\n" +
			"                \"user_name\": \"Pick E Reader <pick.e.reader@example.com>\",\n" +
			"                \"comment\": \"my hola mundo changes\",\n" +
			"                \"revision\": \"c194b49db102b705ebc13e604e490ae13ac92d96\"\n" +
			"              }\n" +
			"            ],\n" +
			"            \"material\": {\n" +
			"              \"description\": \"URL: https://github.com/gocd/gocd, Branch: master\",\n" +
			"              \"fingerprint\": \"f6e7a3899c55e1682ffb00383bdf8f882bcee2141e79a8728254190a1fddcf4f\",\n" +
			"              \"type\": \"Git\",\n" +
			"              \"id\": 1\n" +
			"            },\n" +
			"            \"changed\": false\n" +
			"          }\n" +
			"        ],\n" +
			"        \"trigger_forced\": true,\n" +
			"        \"trigger_message\": \"Forced by anonymous\"\n" +
			"      },\n" +
			"      \"name\": \"pipeline1\",\n" +
			"      \"natural_order\": 10,\n" +
			"      \"can_run\": true,\n" +
			"      \"comment\": null,\n" +
			"      \"stages\": [\n" +
			"        {\n" +
			"          \"name\": \"stage1\",\n" +
			"          \"approved_by\": \"admin\",\n" +
			"          \"jobs\": [\n" +
			"            {\n" +
			"              \"name\": \"job1\",\n" +
			"              \"result\": \"Passed\",\n" +
			"              \"state\": \"Completed\",\n" +
			"              \"id\": 12,\n" +
			"              \"scheduled_date\": 1436172122024\n" +
			"            }\n" +
			"          ],\n" +
			"          \"can_run\": true,\n" +
			"          \"result\": \"Passed\",\n" +
			"          \"approval_type\": \"success\",\n" +
			"          \"counter\": \"1\",\n" +
			"          \"id\": 12,\n" +
			"          \"operate_permission\": true,\n" +
			"          \"rerun_of_counter\": null,\n" +
			"          \"scheduled\": true\n" +
			"        }\n" +
			"      ],\n" +
			"      \"counter\": 10,\n" +
			"      \"id\": 12,\n" +
			"      \"preparing_to_schedule\": false,\n" +
			"      \"label\": \"10\"\n" +
			"    }\n" +
			"  ],\n" +
			"  \"pagination\": {\n" +
			"    \"offset\": 0,\n" +
			"    \"total\": 2,\n" +
			"    \"page_size\": 10\n" +
			"  }\n" +
			"}";

		final GoPipelineHistory history = new Gson().fromJson(json, GoPipelineHistory.class);
		Assert.assertNotNull("history should not be null", history);
		List<GoPipelineInstance> instances = history.getPipelines();
		Assert.assertNotNull("instances should not be null", instances);
		Assert.assertEquals("number of instances", 2, instances.size());
	}
}
