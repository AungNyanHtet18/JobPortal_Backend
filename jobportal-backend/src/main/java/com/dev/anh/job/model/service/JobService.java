package com.dev.anh.job.model.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.entity.Career;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;
import com.dev.anh.job.model.input.JobForm;
import com.dev.anh.job.model.input.JobSearch;
import com.dev.anh.job.model.output.JobDetails;
import com.dev.anh.job.model.output.JobListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.CareerRepo;
import com.dev.anh.job.model.repo.CompanyRepo;
import com.dev.anh.job.model.repo.JobRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class JobService {
	
	private final CompanyRepo companyRepo;
	private final JobRepo jobRepo;
	private final CareerRepo careerRepo;
	
	public PageResult<JobListItem> searchJob(JobSearch jobSearch, int page, int size) {
		return jobRepo.search(queryFunc(jobSearch), countFunc(jobSearch), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<JobListItem>> queryFunc(JobSearch jobSearch) {
		return cb -> {
			var cq = cb.createQuery(JobListItem.class);
			var root = cq.from(Job.class);
			
			var company = root.join(Job_.company, JoinType.INNER);
			
			JobListItem.select(cq, root, company);
			cq.where(jobSearch.where(cb, root, company));
			cq.orderBy(cb.desc(root.get(Job_.id)));
			
			return cq;
		};
	}

	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(JobSearch jobSearch) {
		return cb -> {
			 var cq = cb.createQuery(Long.class);
			 var root = cq.from(Job.class);
			 
			 var company = root.join(Job_.company, JoinType.INNER);
			 
			 cq.select(cb.count(root.get(Job_.id)));
			 cq.where(jobSearch.where(cb, root, company));
			 
			 return cq;
		};
	}
	
	public JobDetails findById(Long id) {
		return jobRepo.findById(id).map(a -> JobDetails.from(a))
					.orElseThrow(() -> new BusinessException("Job with %d is not found".formatted(id)));
	}
	
	public List<JobDetails> findByCompanyId(Long companyId) {
		return jobRepo.findByCompanyId(companyId).stream().map(a -> JobDetails.from(a)).toList();
	}

	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> storeJobInfo(String username, JobForm form) {
		
		var company = companyRepo.findOneByCompanyName(username).orElseThrow(() -> new BusinessException("%s name is not found".formatted(username)));
		var career = careerRepo.findOneByRoleName(form.positionName())
						.orElseGet(() -> {
							 var newCareer = new Career();
							 newCareer.setRoleName(form.positionName());
						return careerRepo.save(newCareer);	 
						});
		
		var job = jobRepo.save(form.entity(company, career));
		
		return new ModificationResult<Long>(job.getId());
	}
		
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> updateJobInfo(Long id, JobForm form) {
		var job = jobRepo.findById(id).orElseThrow(() -> new BusinessException("Job with %d  is not found".formatted(id)));
		var career = careerRepo.findOneByRoleName(form.positionName())
						.orElseGet(() -> {
							 var newCareer = new Career();
							 newCareer.setRoleName(form.positionName());
						return careerRepo.save(newCareer);	 
						});
		
		job.setCareer(career);
		job.setJobPost(form.jobPost());	
		job.setClientName(form.clientName()); 
		job.setLocation(form.location());
		job.setJobDescriptions(form.jobDescriptions());
		job.setJobRequirements(form.jobRequirements());
		job.setJobLevel(form.jobLevel());
		job.setJobType(form.jobType());
		job.setMinSalaryRange(form.minSalaryRange());
		job.setMaxSalaryRange(form.maxSalaryRange());
		job.setDeleted(form.deleted());
		
		jobRepo.save(job);
		return new ModificationResult<Long>(id);
	}
	
	
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<String> deleteJobInfo(Long id) {
		jobRepo.deleteById(id);		
		return new ModificationResult<String>("You successfully deleted job");
	}
}
