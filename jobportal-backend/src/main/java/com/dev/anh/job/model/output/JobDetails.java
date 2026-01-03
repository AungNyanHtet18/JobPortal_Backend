package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Job;

public record JobDetails(
	Long jobId,
	String companyName,
	String companyLocation,
	String companyPhone,
	String companyWebsite,
	String positionName,
	String JobDescription,
	JobLevel jobLevel,
	JobType jobType) {

	public static JobDetails from(Job entity) {
		return new JobDetails(
			entity.getId(), 
			entity.getCompany().getAccount().getName(),
			entity.getCompany().getLocation(), 
			entity.getCompany().getPhone(), 
			entity.getCompany().getWebsiteUrl(), 
			entity.getPositionName(), 
			entity.getJobDescription(), 
			entity.getJobLevel(), 
			entity.getJobType());
	}

}
