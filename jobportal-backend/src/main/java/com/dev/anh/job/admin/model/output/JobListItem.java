package com.dev.anh.job.admin.model.output;

import java.time.LocalDateTime;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;


public record JobListItem(
	Long id,
	String jobName,
	String CompanyName,
	String clientName,
	JobLevel jobLevel,
	JobType jobType,
	Double minSalaryRange,
	Double maxSalaryRange,
	LocalDateTime createAt) {

}
