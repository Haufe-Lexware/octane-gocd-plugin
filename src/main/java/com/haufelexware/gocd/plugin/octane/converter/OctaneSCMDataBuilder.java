package com.haufelexware.gocd.plugin.octane.converter;

import com.haufelexware.gocd.dto.*;
import com.hp.octane.integrations.dto.DTOFactory;
import com.hp.octane.integrations.dto.scm.SCMCommit;
import com.hp.octane.integrations.dto.scm.SCMData;
import com.hp.octane.integrations.dto.scm.SCMRepository;
import com.hp.octane.integrations.dto.scm.SCMType;

import java.util.ArrayList;

/**
 * This builder helps retrieving the {@link SCMData} from a {@link GoPipelineInstance}.
 */
public class OctaneSCMDataBuilder {

	public SCMData retrieveFrom(GoPipelineInstance pipelineInstance) {
		SCMData scmData = DTOFactory.getInstance().newDTO(SCMData.class);
		GoBuildCause buildCause = pipelineInstance.getBuildCause();
		if (buildCause != null && buildCause.getMaterialRevision() != null) {
			// search for the repository.
			for (GoMaterialRevision materialRevision : buildCause.getMaterialRevision()) {
				if (materialRevision.getMaterial() != null && !"Pipeline".equals(materialRevision.getMaterial().getType())) {
					if (materialRevision.getMaterial() != null) {
						scmData.setRepository(retrieveFrom(materialRevision.getMaterial()));
					}
					if (materialRevision.getModifications() != null) {
						scmData.setCommits(new ArrayList<SCMCommit>());
						for (GoModification modification : materialRevision.getModifications()) {
							scmData.getCommits().add(DTOFactory.getInstance().newDTO(SCMCommit.class)
								.setUser(modification.getUserName())
								.setUserEmail(modification.getEmailAddress())
								.setComment(modification.getComment())
								.setRevId(modification.getRevision())
								.setTime(modification.getModifiedTime()));
						}
					}
				}
			}
		}
		return scmData;
	}

	public SCMRepository retrieveFrom(GoMaterial material) {
		SCMRepository repository = DTOFactory.getInstance().newDTO(SCMRepository.class)
			.setType(SCMType.fromValue(material.getType().toLowerCase()));

		for (String fragment : material.getDescription().split(",")) {
			fragment = fragment.trim();
			if (fragment.startsWith("URL:")) {
				repository.setUrl(fragment.substring(4).trim());
			} else if (fragment.startsWith("Branch:")) {
				repository.setBranch(fragment.substring(7).trim());
			}
		}
		return repository;
	}
}
