package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Applicant;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ApplicantForm(
	
	 String applicantName,
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

	public Applicant entity(Account account) {
		var applicant = new Applicant();
		applicant.setAccount(account);
		applicant.setGender(gender);
		applicant.setHighestEducationalAttainment(highestEducationalAttainment);;
		applicant.setResumeUrl(resumeUrl);
		applicant.setSkills(skills);
		applicant.setCurrentJob(currentJob);
		applicant.setProfessionalSummary(professionalSummary);
		applicant.setContactDetail(contactDetail);
		applicant.setAddress(address);
	
		return applicant;
	}
		
}
