package com.dev.anh.job.model.output;
import java.time.LocalDate;

import com.dev.anh.job.model.entity.Experience;

public record ApplicantExperienceDetails(
	Long id,
	String companyName,
	String position,
	LocalDate joinedDate,
	LocalDate leftDate,
	Boolean currentlyWorking,
	String experienceDescription) {

	public static ApplicantExperienceDetails from(Experience experience) {
		return new ApplicantExperienceDetails(
			   experience.getId(),
			   experience.getCompanyName(),
			   experience.getPosition(),
			   experience.getJoinedDate(),
			   experience.getLeftDate(),
			   experience.isCurrentlyWorking(),
			   experience.getExperienceDescription()
			  );
	}
	
	
}
