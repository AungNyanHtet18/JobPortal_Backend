package com.dev.anh.job.model.input;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Company;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyForm(
	
	String companyName,
	@NotBlank(message = "Please enter your company address.")
	String location,
	@NotBlank(message = "Please enter your phone number.")
	String phone,
	String website,
	@NotBlank(message = "Please enter your description.")
	String description){

	public Company entity(Account account) {
		var company = new Company();
		company.setAccount(account);
		company.setLocation(location);
		company.setPhone(phone);
		company.setWebsite(website);
		company.setDescription(description);
		
		return company;
	}

}
