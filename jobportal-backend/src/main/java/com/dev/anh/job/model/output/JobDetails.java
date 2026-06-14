package com.dev.anh.job.model.output;

import java.util.List;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Job;

public record JobDetails(
	Long jobId,
	String jobCategory,
	String companyName,
	String companyLocation,
	String companyPhone,
	String companyWebsite,
	String companyImage,
	String positionName,
	String clientName,
	List<String> jobDescription,
	List<String> jobRequirement,
	Double salary,
	Integer jobPost,
	JobLevel jobLevel,
	JobType jobType) {

	public static JobDetails from(Job entity) {
		return new JobDetails(
			entity.getId(), 
			entity.getCareer().getRoleName(),
			entity.getCompany().getAccount().getName(),
			entity.getCompany().getLocation(), 
			entity.getCompany().getPhone(), 
			entity.getCompany().getWebsiteUrl(),
			entity.getCompany().getProfilePhoto(),
			entity.getCareer().getRoleName(), 
			entity.getClientName(),
			entity.getJobDescriptions(), 
			entity.getJobRequirements(),
			entity.getSalary(),
			entity.getJobPost(),
			entity.getJobLevel(), 
			entity.getJobType());
	}

}
