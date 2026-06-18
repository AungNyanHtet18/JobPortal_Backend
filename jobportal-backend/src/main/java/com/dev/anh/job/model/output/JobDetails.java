package com.dev.anh.job.model.output;

import java.util.List;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Job;

public record JobDetails(
	Long jobId,
	String jobCategory,
	String companyName,
	String companyPhone,
	String companyWebsite,
	String companyImage,
	String positionName,
	String clientName,
	String jobLocation,
	List<String> jobDescription,
	List<String> jobRequirement,
	Double minSalaryRange,
	Double maxSalaryRange,
	Integer jobPost,
	JobLevel jobLevel,
	JobType jobType,
	boolean deleted) {

	public static JobDetails from(Job entity) {
		return new JobDetails(
			entity.getId(), 
			entity.getCareer().getRoleName(),
			entity.getCompany().getAccount().getName(),
			entity.getCompany().getPhone(), 
			entity.getCompany().getWebsiteUrl(),
			entity.getCompany().getProfilePhoto(),
			entity.getCareer().getRoleName(), 
			entity.getClientName(),
			entity.getLocation(),
			entity.getJobDescriptions(), 
			entity.getJobRequirements(),
			entity.getMinSalaryRange(),
			entity.getMaxSalaryRange(),
			entity.getJobPost(),
			entity.getJobLevel(), 
			entity.getJobType(),
			entity.isDeleted());
	}

}
