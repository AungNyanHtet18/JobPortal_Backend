package com.dev.anh.job.model.input;

import java.util.List;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Career;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Job;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record JobForm(

	Integer jobPost,
	
	String clientName,

	@NotBlank(message = "Please enter job position name.")
 	String positionName,
 	
 	@NotEmpty(message = "Please provide at least one job description bullect point")
 	@Size(max=20, message = "You cannot add more than 20 description points.")
	List<@NotBlank(message = "Job description point cannot be blank." ) String> jobDescriptions,
	
	@NotEmpty(message = "Please provide at least one job requirement bullect point")
 	@Size(max=20, message = "You cannot add more than 20 requirement points.")
 	List<@NotBlank(message = "Job requirement point cannt be blank.") String> jobRequirements,
 	 	
 	@NotNull(message = "Please enter job level.")
 	JobLevel jobLevel,
 	
 	@NotNull(message = "Please enter job type.")
 	JobType jobType,
	
 	@NotNull(message = "Please enter salary.")
	Double salary,
 	
	boolean deleted) {

	public Job entity(Company company, Career career) {
		var job = new Job();
		
		job.setCareer(career);
		job.setCompany(company);
		job.setJobPost(jobPost);
		job.setClientName(clientName);
		job.setJobDescriptions(jobDescriptions);
		job.setJobRequirements(jobRequirements);
		job.setJobLevel(jobLevel);
		job.setJobType(jobType);
		job.setSalary(salary);
		job.setDeleted(deleted);
	
		
		return job;
	}

}
