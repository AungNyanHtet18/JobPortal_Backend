package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.Career;

public record ApplicantCareerRoleDetails(
	String roleName	) {

	public static ApplicantCareerRoleDetails from(Career careerRole) {
		 return new ApplicantCareerRoleDetails(
			careerRole.getRoleName());
	}
	
}
