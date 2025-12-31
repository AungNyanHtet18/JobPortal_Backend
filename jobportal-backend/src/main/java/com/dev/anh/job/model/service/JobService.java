package com.dev.anh.job.model.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.input.JobForm;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.CompanyRepo;
import com.dev.anh.job.model.repo.JobRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobService {
	
	private final CompanyRepo companyRepo;
	private final JobRepo jobRepo;
	
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> storeJobInfo(String username, JobForm form) {
		
		var company = companyRepo.findOneByCompanyName(username).orElseThrow(() -> new BusinessException("%s name is not found".formatted(username)));
	    var job = jobRepo.save(form.entity(company));
		
		return new ModificationResult<Long>(job.getId());
	}
	
	
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> updateJobInfo(Long id, JobForm form) {
		
		var job = jobRepo.findById(id).orElseThrow(() -> new BusinessException("Job with %d  is not found".formatted(id)));
		
		job.setPositionName(form.positionName());
		job.setSummaryForPosition(form.summaryForPosition());
		job.setSalary(form.salary());
		job.setJobLevel(form.jobLevel());
		job.setJobType(form.jobType());
		
		jobRepo.save(job);
		
		return new ModificationResult<Long>(id);
	}
}
