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
	String companyImage,
	String positionName,
	String jobDescription,
	Double salary,
	JobLevel jobLevel,
	JobType jobType) {

	public static JobDetails from(Job entity) {
		return new JobDetails(
			entity.getId(), 
			entity.getCompany().getAccount().getName(),
			entity.getCompany().getLocation(), 
			entity.getCompany().getPhone(), 
			entity.getCompany().getWebsiteUrl(),
			entity.getCompany().getProfilePhoto(),
			entity.getPositionName(), 
			entity.getJobDescription(), 
			entity.getSalary(),
			entity.getJobLevel(), 
			entity.getJobType());
	}

}
