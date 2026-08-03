package com.dev.anh.job.model.output;

import java.util.List;

import com.dev.anh.job.model.consts.IndustryType;
import com.dev.anh.job.model.entity.Company;

public record CompanyDetails(
	Long id,
	String companyName,
	String companyEmail,
	IndustryType industryType,
	String location,
	String phone,
	String websiteUrl,
	String description,
	String profileImage,
	Integer totalPostedJobs,
	List<UploadedJob> uploadedJob){

	public static CompanyDetails from(Company entity) {
		var uploadedJobs = entity.getJobs().stream().map(UploadedJob::from).toList();		
		return new CompanyDetails(
				    entity.getId(),
					entity.getAccount().getName(), 
				    entity.getAccount().getEmail(),
				    entity.getIndustryType(),
					entity.getLocation(), 
					entity.getPhone(), 
					entity.getWebsiteUrl(), 
					entity.getDescription(),
					entity.getProfilePhoto(),
					entity.getJobs().size(),				
					uploadedJobs);
	}

}
