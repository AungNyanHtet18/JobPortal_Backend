package com.dev.anh.job.model.input;

import java.time.LocalDate;

import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Experience;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExperienceForm(
	@NotBlank(message = "Please fill your previous company name")
	String companyName,
	@NotBlank(message = "Please fill your previous position")
	String position,
	@NotNull(message = "Please enter completion date.")
	LocalDate joinedDate,
	LocalDate leftDate,
	Boolean currentlyWorking,
	String experienceDescription) {
	
	public static Experience ApplicantJobExperience(Applicant applicant, ExperienceForm form) {
		var experience = new Experience();
		experience.setApplicant(applicant);
		experience.setCompanyName(form.companyName());
		experience.setPosition(form.position());
		experience.setJoinedDate(form.joinedDate());
		experience.setLeftDate(form.leftDate());
		experience.setCurrentlyWorking(form.currentlyWorking());
		experience.setExperienceDescription(form.experienceDescription());
		return experience;
	}

}
