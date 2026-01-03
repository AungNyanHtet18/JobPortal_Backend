package com.dev.anh.job.model.output;

import java.util.List;

import com.dev.anh.job.model.entity.Company;

public record CompanyDetails(
	String companyName,
	String location,
	String phone,
	String websiteUrl,
	String description,
	List<UploadedJob> uploadedJob){

	public static CompanyDetails from(Company entity) {
		var uploadedJob = entity.getJobs().stream().map(UploadedJob::from).toList();		
		return new CompanyDetails(
					entity.getAccount().getName(), 
					entity.getLocation(), 
					entity.getPhone(), 
					entity.getWebsiteUrl(), 
					entity.getDescription(), 
					uploadedJob);
	}

	
	
	
	
}
