package com.dev.anh.job.model.output;

import java.util.List;
import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Applicant;

public record ApplicantDetails(
	Long id,
	String name,
	String email,
	Long followerCount,
	Long followingCount,
	Gender gender,
	String professionalSummary,
	String contactDetail,
	String address,
	List<ApplicantExperienceDetails> experience,
	List<ApplicantSocialLinkDetails> socialLink,
	List<ApplicantEducationDetails> education,
	List<ApplicantCareerRoleDetails> careerRole,
	List<ApplicantSkillDetails> skill,
	List<ApplicantLanguageDetails> language,
	String profileImage,
	String resume,
	String cvForm) {

	public static ApplicantDetails from(Applicant entity, Long followerCount, Long followingCount) {
		var experience = entity.getExperiences().stream().map(ApplicantExperienceDetails:: from).toList();
		var link = entity.getLinks().stream().map(ApplicantSocialLinkDetails:: from).toList();
		var education = entity.getEducations().stream().map(ApplicantEducationDetails:: from).toList();
		var careerRole = entity.getCareerRoles().stream().map(ApplicantCareerRoleDetails:: from).toList();
		var skill = entity.getSkills().stream().map(ApplicantSkillDetails:: from).toList();
		var language = entity.getLanguages().stream().map(ApplicantLanguageDetails:: from).toList();
		
		return new ApplicantDetails(
				entity.getId(),			
				entity.getAccount().getName(), 
				entity.getAccount().getEmail(), 
				followerCount,
				followingCount,
				entity.getGender(),
				entity.getProfessionalSummary(),
				entity.getContactDetail(), 
				entity.getAddress(),
				experience,
				link,
				education,
				careerRole,
				skill,
				language,
				entity.getProfilePhoto(),
				entity.getResume(),
				entity.getCvForm());
				}	
}
