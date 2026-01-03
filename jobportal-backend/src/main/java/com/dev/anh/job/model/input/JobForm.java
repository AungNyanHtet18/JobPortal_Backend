package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record JobForm(
	
	@NotBlank(message = "Please enter job position name.")
 	String positionName,
 	@NotBlank(message = "Please enter summary for position.")
 	String jobDescription,
 	@NotNull(message = "Please enter salary.")
 	Double salary,
 	@NotNull(message = "Please enter job level.")
 	JobLevel jobLevel,
 	@NotNull(message = "Please enter job type.")
 	JobType jobType,
 	boolean deleted) {

	public Job entity(Company company) {
		var job = new Job();
		job.setPositionName(positionName);
		job.setJobDescription(jobDescription);
		job.setSalary(salary);
		job.setJobLevel(jobLevel);
		job.setJobType(jobType);
		job.setCompany(company);
		
		return job;
	}

}
