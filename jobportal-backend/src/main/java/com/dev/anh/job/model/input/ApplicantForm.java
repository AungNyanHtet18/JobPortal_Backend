package com.dev.anh.job.model.input;

import java.util.List;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Applicant;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ApplicantForm(
	 @NotBlank(message = "Please enter your applicant name.")
	 String applicantName,
	 @NotNull(message = "Please select gender")
	 Gender gender,
	 String professionalSummary,
	 @NotBlank(message = "Please fill your contact detail.")
	 String contactDetail,
	 @NotBlank(message = "Please fill your address.")
	 String address,
	 List<@Valid ExperienceForm> experiences,
	 List<@Valid SocialLinkForm> socialLinks,
	 List<@Valid EducationForm> educations,
	 @NotEmpty(message = "Please select at least one career role.")
	 List<@Valid CareerRoleForm> careerRoles,
	 List<@Valid SkillForm> skills,
	 List<@Valid LanguageForm> languages ) {

	public Applicant entity(Account account) {
		var applicant = new Applicant();
		applicant.setAccount(account);
		applicant.setGender(gender);
		applicant.setProfessionalSummary(professionalSummary);
		applicant.setContactDetail(contactDetail);
		applicant.setAddress(address);
		
		return applicant;
	}
		
}
