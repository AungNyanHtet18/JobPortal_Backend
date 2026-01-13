package com.dev.anh.job.model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.consts.Status;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk_;
import com.dev.anh.job.model.output.JobApplicationListItem;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.model.repo.JobApplyRepo;
import com.dev.anh.job.model.repo.JobRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobApplyService {

	private final ApplicantRepo applicantRepo;
	private final JobRepo jobRepo;
	private final JobApplyRepo jobApplyRepo;
	
	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<String> apply(String username,  long jobId) {
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("%s name is not found".formatted(username)));
		var job = jobRepo.findById(jobId).orElseThrow(() -> new BusinessException("This %s id  is not found".formatted(jobId)));
		
		
		var jobApplyPk = new JobApplyPk(applicant.getId(), job.getId());
		
		var jobApply = new JobApply();
		jobApply.setId(jobApplyPk);
		jobApply.setApplicant(applicant);
		jobApply.setJob(job);
		jobApply.setStatus(Status.Pending);
		
		jobApplyRepo.save(jobApply);
		
		
		return new ModificationResult<String>("You successfully applied job");
	}

	@Transactional
	@PreAuthorize("#username eq authentication.name")
	public ModificationResult<String> cancel(String username, long jobId) {
	   var jobApply = jobApplyRepo.findOneByApplicantandJob(username, jobId).orElseThrow(() -> new BusinessException("This %s id  is not found".formatted(jobId)));
	   jobApplyRepo.delete(jobApply);
	  
	   return new ModificationResult<String>("You canceled applied job");
	}

	
	public ModificationResult<List<JobApplicationListItem>> applyingApplicantInfo(long jobId) {
	    var jobApplicationList    = jobRepo.search(queryFunc(jobId));
		
	    return new ModificationResult<List<JobApplicationListItem>>(jobApplicationList);
	}
	
	
	private Function<CriteriaBuilder, CriteriaQuery<JobApplicationListItem>> queryFunc(Long jobId) {
		 return cb -> {
			  var cq = cb.createQuery(JobApplicationListItem.class);
			  var root = cq.from(JobApply.class);
			  
			  JobApplicationListItem.select(cq, root);;
			  
			  var param = new ArrayList<Predicate>();
			  
			  if(null != jobId) {
				  param.add(cb.equal(root.get(JobApply_.id).get(JobApplyPk_.jobId), jobId ));
			  }
			  
			  cq.where(param.toArray(size -> new Predicate[size]));
			  cq.orderBy(cb.asc(root.get(JobApply_.createAt)));
			  
			  return cq;
		 };
	}
	
	
	
	
	

	

	
	

}
