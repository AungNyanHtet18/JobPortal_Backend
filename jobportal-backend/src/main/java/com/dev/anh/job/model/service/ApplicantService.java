package com.dev.anh.job.model.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicantService {

	private final AccountRepo accountRepo;
	private final ApplicantRepo applicantRepo;
	
	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<Long> storeApplicantInfo(String username, ApplicantForm form) {
		
		var account = accountRepo.findOneByEmail(username)
						.orElseThrow(() -> new BusinessException("Account that using %s is not found".formatted(username)));
		
		 applicantRepo.save(form.entity(account));
		
		return  new ModificationResult<Long>(account.getId());
	} 
	
	
	
	
	
	
}
