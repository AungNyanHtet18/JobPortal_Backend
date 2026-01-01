package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Applicant;

public record ApplicantDetails(
	String name,
	String email,
	Gender gender,
	String skills,
	String  highestEducationalAttainment,
	String professionalSummary,
	String currentJob,
	String contactDetail,
	String address) {

	public static ApplicantDetails from(Applicant entity) {
		return new ApplicantDetails(
				entity.getAccount().getName(), 
				entity.getAccount().getEmail(), 
				entity.getGender(), 
				entity.getSkills(), 
				entity.getHighestEducationalAttainment(), 
				entity.getProfessionalSummary(), 
				entity.getCurrentJob(), 
				entity.getContactDetail(), 
				entity.getAddress());
	}	
}
