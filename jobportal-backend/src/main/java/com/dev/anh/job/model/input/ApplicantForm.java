package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.Gender;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicantForm(
	
	 @NotNull(message = "Please select gender")
	 Gender gender,
	 String highestEducationalAttainment,
	 String resumeUrl,
	 @NotBlank(message = "Please fill your professional skills.")
	 String skills,
	 String currentJob,
	 String professionalSummary,
	 @NotBlank(message = "Please fill your contact detail.")
	 String contactDetail,
	 @NotBlank(message = "Please fill your address.")
	 String address) {
		
}
