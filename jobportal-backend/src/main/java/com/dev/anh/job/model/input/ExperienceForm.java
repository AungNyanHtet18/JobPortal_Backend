package com.dev.anh.job.model.input;

import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Experience;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ExperienceForm(
	@NotBlank(message = "Please fill your previous company name")
	String companyName,
	@NotBlank(message = "Please fill your previous position")
	String position,
	@NotNull(message = "Please fill your experience year")
	@Min(value = 0, message = "Experience year must be 0 or greater")
	int year) {
	
	public static Experience ApplicantJobExperience(Applicant applicant, ExperienceForm form) {
		var experience = new Experience();
		experience.setApplicant(applicant);
		experience.setCompanyName(form.companyName());
		experience.setPosition(form.position());
		experience.setYears(form.year());
		return experience;
	}

}
