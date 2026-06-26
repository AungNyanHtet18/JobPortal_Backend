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
	
	@NotBlank(message = "Please enter location for job.")
	String location,

	@NotBlank(message = "Please enter job position name.")
 	String positionName,
 	
 	@NotEmpty(message = "Please provide at least one job description bullect point")
	List<@Size(max=200, message = "You cannot add more than 20 description points.") @NotBlank(message = "Job description point cannot be blank." ) String> jobDescriptions,
	
	@NotEmpty(message = "Please provide at least one job requirement bullect point")
 	List<@Size(max=200, message = "You cannot add more than 20 requirement points.") @NotBlank(message = "Job requirement point cannt be blank.") String> jobRequirements,
 	 	
 	@NotNull(message = "Please select job level.")
 	JobLevel jobLevel,
 	
 	@NotNull(message = "Please select job type.")
 	JobType jobType,
		
	@NotNull(message = "Please enter minimum salary range.")
	Double minSalaryRange,
 	
	@NotNull(message = "Please enter maximum salary range.")
	Double maxSalaryRange,
	
	boolean deleted) {

	public Job entity(Company company, Career career) {
		var job = new Job();
		
		job.setCareer(career);
		job.setCompany(company);
		job.setJobPost(jobPost);
		job.setClientName(clientName);
		job.setLocation(location);
		job.setJobDescriptions(jobDescriptions);
		job.setJobRequirements(jobRequirements);
		job.setJobLevel(jobLevel);
		job.setJobType(jobType);
		job.setMinSalaryRange(minSalaryRange);
		job.setMaxSalaryRange(maxSalaryRange);
		job.setDeleted(deleted);
	
		return job;
	}

}
