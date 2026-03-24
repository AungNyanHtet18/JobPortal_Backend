package com.dev.anh.job.model.output;
import com.dev.anh.job.model.entity.Experience;

public record ApplicantExperienceDetails(
	Long id,
	String companyName,
	String position,
	int years) {

	public static ApplicantExperienceDetails from(Experience experience) {
		return new ApplicantExperienceDetails(
			   experience.getId(),
			   experience.getCompanyName(),
			   experience.getPosition(),
			   experience.getYears());
	}
	
	
}
