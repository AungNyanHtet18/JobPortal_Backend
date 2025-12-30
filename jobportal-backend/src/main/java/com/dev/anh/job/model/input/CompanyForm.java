package com.dev.anh.job.model.input;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Company;

import jakarta.validation.constraints.NotNull;

public record CompanyForm(
	
	String companyName,
	@NotNull(message = "Please enter your company address.")
	String location,
	String phone,
	String website,
	@NotNull(message = "Please enter your description.")
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
