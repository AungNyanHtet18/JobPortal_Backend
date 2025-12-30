package com.dev.anh.job.model.service;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.input.CompanyForm;
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
		
		
		
		
		
		
		
		return null;
	}

}
