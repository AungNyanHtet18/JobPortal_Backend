package com.dev.anh.job.model.service;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dev.anh.job.model.input.CompanyForm;
import com.dev.anh.job.model.output.CompanyDetails;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.CompanyRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

	private final AccountRepo accountRepo;
	private final CompanyRepo companyRepo;
	
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount') and #username eq authentication.name")
	public ModificationResult<Long> storeCompanyInfo(String username, CompanyForm form) {
		
		var account = accountRepo.findOneByEmail(username)
							.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
		
		
		if(StringUtils.hasLength(form.companyName())) {
			 account.setName(form.companyName());
			 accountRepo.saveAndFlush(account);
		}
		
		companyRepo.save(form.entity(account));
		
		return new ModificationResult<Long>(account.getId());
	}
	 
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> updateCompanyInfo(Long id, CompanyForm form) {
		
		var account = accountRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));
							 
		var company = companyRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Company with %s id is not found".formatted(id)));
		
		
		if(StringUtils.hasLength(form.companyName())) {
			account.setName(form.companyName()); 
			accountRepo.saveAndFlush(account);
		}
		
		company.setAccount(account);
		company.setLocation(form.location());
		company.setPhone(form.phone());
		company.setWebsiteUrl(form.websiteUrl());
		company.setDescription(form.description());
	
		companyRepo.save(company);
		
		return new ModificationResult<Long>(id);
	}
	
	public CompanyDetails findById(Long id) {
		 return companyRepo.findById(id)
				 .map(a -> CompanyDetails.from(a)).orElseThrow(() -> new BusinessException("Company with %d is not found".formatted(id)));
	}
}
