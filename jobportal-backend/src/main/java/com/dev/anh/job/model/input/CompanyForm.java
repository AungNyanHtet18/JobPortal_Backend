package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.IndustryType;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Company;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyForm(
	@NotNull(message = "Please enter your industry type.")	
	IndustryType industryType,
	@NotBlank(message = "Please enter your company name.")
	String companyName,
	@NotBlank(message = "Please enter your company location.")
	String location,
	@NotBlank(message = "Please enter your company phone numbers.")
	String phone,
	String websiteUrl,
	@NotBlank(message = "Please enter your company goals.")
	String description){

	public Company entity(Account account) {
		var company = new Company();
		company.setAccount(account);
		company.setIndustryType(industryType);
		company.setLocation(location);
		company.setPhone(phone);
		company.setWebsiteUrl(websiteUrl);
		company.setDescription(description);
		return company;
	}

}
