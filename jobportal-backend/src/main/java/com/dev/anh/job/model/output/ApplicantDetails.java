package com.dev.anh.job.model.output;

import java.util.List;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Applicant;

public record ApplicantDetails(
	String name,
	String email,
	Gender gender,
	List<String> skills,
	String  highestEducationalAttainment,
	String professionalSummary,
	String contactDetail,
	String address) {

	public static ApplicantDetails from(Applicant entity) {
		return new ApplicantDetails(
				entity.getAccount().getName(), 
				entity.getAccount().getEmail(), 
				entity.getGender(), 
				List.of(entity.getSkills().split(",")), 
				entity.getHighestEducationalAttainment(), 
				entity.getProfessionalSummary(),
				entity.getContactDetail(), 
				entity.getAddress());
	}	
}
