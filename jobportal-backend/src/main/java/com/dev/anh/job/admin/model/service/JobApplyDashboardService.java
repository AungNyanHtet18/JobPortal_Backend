package com.dev.anh.job.admin.model.service;

import java.util.List;
import java.util.function.Function;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.admin.model.input.ApplicationSearch;
import com.dev.anh.job.admin.model.output.ApplicationListItem;
import com.dev.anh.job.admin.model.output.MostAppliedJobListItem;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.JobApplyRepo;
import com.dev.anh.job.model.repo.JobRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobApplyDashboardService {

	private final JobRepo jobRepo;
	private final JobApplyRepo jobApplyRepo;
	
	public PageResult<ApplicationListItem> searchApplications(ApplicationSearch applicationSearch, int page, int size) {
		return jobApplyRepo.search(queryFunc(applicationSearch), countFunc(applicationSearch) , page, size);
	}
	
	public List<MostAppliedJobListItem> getMostAppliedJobs() {
		return jobRepo.search(queryFuncForMostAppliedJobList(), 5);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<ApplicationListItem>> queryFunc(ApplicationSearch applicationSearch) {
		return cb -> {
			 var cq = cb.createQuery(ApplicationListItem.class);
			 var root = cq.from(JobApply.class);
			 
			 var job = root.join(JobApply_.job);
			 var applicant = root.join(JobApply_.applicant);
			 
			 ApplicationListItem.select(cq, root, job, applicant);
			 cq.where(applicationSearch.where(cb, root, job, applicant));
			 cq.orderBy(cb.asc(root.get(JobApply_.createAt)));
			 
			 return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(ApplicationSearch applicantSearch) {
		 return cb -> {
			 var cq = cb.createQuery(Long.class);
			 var root = cq.from(JobApply.class);
			 
			 var job = root.join(JobApply_.job);
			 var applicant = root.join(JobApply_.applicant);
			 
			cq.select(cb.count(root)); //SELECT COUNT(*) FROM job_apply;
			cq.where(applicantSearch.where(cb, root, job, applicant));			 
			
			return cq;
		 };
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<MostAppliedJobListItem>> queryFuncForMostAppliedJobList() {
		return cb -> {
			 var cq = cb.createQuery(MostAppliedJobListItem.class);
			 var root = cq.from(Job.class);
			 MostAppliedJobListItem.select(cq, cb, root);
			 return cq;
		 };
	}	
}

