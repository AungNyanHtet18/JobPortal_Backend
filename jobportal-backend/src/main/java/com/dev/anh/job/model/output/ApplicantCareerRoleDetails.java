package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.CareerRole;

public record ApplicantCareerRoleDetails(
	String roleName	) {

	public static ApplicantCareerRoleDetails from(CareerRole careerRole) {
		 return new ApplicantCareerRoleDetails(
			careerRole.getRoleName());
	}
	
}
