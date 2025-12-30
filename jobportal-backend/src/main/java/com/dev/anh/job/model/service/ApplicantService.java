package com.dev.anh.job.model.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import ch.qos.logback.core.util.StringUtil;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

	private final AccountRepo accountRepo;
	private final ApplicantRepo applicantRepo;
	
	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<Long> storeApplicantInfo(String username, ApplicantForm form) {
		
		var account = accountRepo.findOneByEmail(username)
						.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
		
		
		 if(StringUtils.hasLength(form.applicantName())) {
			  account.setName(form.applicantName());
			  accountRepo.saveAndFlush(account);
		 }
		
		 applicantRepo.save(form.entity(account));
		
		return  new ModificationResult<Long>(account.getId());
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<Long> updateApplicantInfo(Long id, ApplicantForm form) {
		
		var account = accountRepo.findById(id)
						 .orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));
		
		
		var applicant = applicantRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Applicant with %s id is nod found".formatted(id)));
		
		 if(StringUtils.hasLength(form.applicantName())) {
			  account.setName(form.applicantName());
			  accountRepo.saveAndFlush(account);
		 }
		
		applicant.setAccount(account);
		applicant.setGender(form.gender());
		applicant.setHighestEducationalAttainment(form.highestEducationalAttainment());
		applicant.setResumeUrl(form.resumeUrl());
		applicant.setSkills(form.skills());
		applicant.setCurrentJob(form.currentJob());
		applicant.setProfessionalSummary(form.professionalSummary());
		applicant.setContactDetail(form.contactDetail());
		applicant.setAddress(form.address());
		
	    applicantRepo.save(applicant);

		return new ModificationResult<Long>(id);
	} 

	
}
