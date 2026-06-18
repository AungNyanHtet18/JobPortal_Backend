package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Job;

public record UploadedJob(
	String postionName,
	Double maxSalary,
	Double minSalary,
	Long jobId,
	JobLevel jobLevel,
	JobType jobType,
	String jobLocation) {

	public static UploadedJob from(Job job) {
		 return new UploadedJob(
				 job.getCareer().getRoleName(), 
				 job.getMaxSalaryRange(),
				 job.getMinSalaryRange(),
				 job.getId(),
				 job.getJobLevel(), 
				 job.getJobType(),
				 job.getLocation());
	}
}
