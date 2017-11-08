package com.haufelexware.gocd.dto;

import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * This test ensures that a stageInstance can be correctly parsed.
 */
public class GoStageInstanceTest {

	@Test
	public void testParsingJson() {
		final String json = "{\"result\":\"Passed\",\"jobs\":[{\"result\":\"Passed\",\"agent_uuid\":\"777c0391-2d5c-4276-955a-fbc908c6b3eb\",\"stage_name\":null,\"scheduled_date\":1510140454431,\"job_state_transitions\":[{\"state\":\"Scheduled\",\"state_change_time\":1510140454431,\"id\":883},{\"state\":\"Assigned\",\"state_change_time\":1510140467157,\"id\":884},{\"state\":\"Preparing\",\"state_change_time\":1510140477176,\"id\":885},{\"state\":\"Building\",\"state_change_time\":1510140479624,\"id\":886},{\"state\":\"Completing\",\"state_change_time\":1510140495227,\"id\":887},{\"state\":\"Completed\",\"state_change_time\":1510140495243,\"id\":888}],\"pipeline_counter\":null,\"state\":\"Completed\",\"original_job_id\":null,\"pipeline_name\":null,\"name\":\"BuildAndTest\",\"stage_counter\":null,\"rerun\":false,\"id\":804}],\"rerun_of_counter\":null,\"approved_by\":\"changes\",\"pipeline_counter\":33,\"pipeline_name\":\"OctanePluginBuild\",\"name\":\"BuildBcrypt\",\"approval_type\":\"success\",\"artifacts_deleted\":false,\"id\":804,\"counter\":1,\"fetch_materials\":true,\"clean_working_directory\":false}";
		GoStageInstance stage = new Gson().fromJson(json, GoStageInstance.class);
		Assert.assertNotNull("stage should not be null", stage);
		List<GoJobInstance> jobs = stage.getJobs();
		Assert.assertNotNull("jobs should not be null", jobs);
		Assert.assertEquals("number of jobs", 1, jobs.size());
		List<GoJobStateTransition> transitions =   jobs.get(0).getJobStateTransitions();
		Assert.assertNotNull("transitions should not be null", transitions);
		Assert.assertEquals("number of transitions", 6, transitions.size());
	}
}
