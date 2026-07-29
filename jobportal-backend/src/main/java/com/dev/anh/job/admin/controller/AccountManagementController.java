package com.dev.anh.job.admin.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.admin.model.input.AccountSearch;
import com.dev.anh.job.admin.model.input.ApplicantSearch;
import com.dev.anh.job.admin.model.input.CompanySearch;
import com.dev.anh.job.admin.model.output.AccountListItem;
import com.dev.anh.job.admin.model.output.ApplicantListItem;
import com.dev.anh.job.admin.model.output.CompanyListItem;
import com.dev.anh.job.admin.model.service.AccountManagementService;
import com.dev.anh.job.model.output.PageResult;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class AccountManagementController {

	private final AccountManagementService accountManagementService;
	
	@GetMapping("account")
	@PreAuthorize("hasAuthority('Admin')")
	PageResult<AccountListItem> searchAccount(AccountSearch accountSearch,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		 return accountManagementService.searchAccount(accountSearch, page, size);
	}
	
	@GetMapping("applicant")
	@PreAuthorize("hasAuthority('Admin')")
	PageResult<ApplicantListItem> searchApplicant(ApplicantSearch applicantSearch,
				@RequestParam(required = false, defaultValue = "0") int page,
				@RequestParam(required = false, defaultValue = "10") int size) {
		 return accountManagementService.searchApplicant(applicantSearch, page, size);
	}
	
	@GetMapping("company")
	@PreAuthorize("hasAuthority('Admin')")
	PageResult<CompanyListItem> searchCompany(CompanySearch companySearch,
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false, defaultValue = "10") int size) {
		 return accountManagementService.searchCompany(companySearch, page, size);
	}

}
