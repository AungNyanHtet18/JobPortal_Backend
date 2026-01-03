package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Job;

public record UploadedJob(
	String postionName,
	Double salary,
	JobLevel jobLevel,
	JobType jobType) {

	public static UploadedJob from(Job job) {
		 return new UploadedJob(
				 job.getPositionName(), 
				 job.getSalary(), 
				 job.getJobLevel(), 
				 job.getJobType());
	}
}
