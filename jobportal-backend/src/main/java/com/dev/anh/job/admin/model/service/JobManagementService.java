package com.dev.anh.job.admin.model.service;

import java.util.function.Function;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.admin.model.input.JobSearch;
import com.dev.anh.job.admin.model.output.JobListItem;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.JobRepo;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobManagementService {
	
	private final JobRepo jobRepo;
	
	public PageResult<JobListItem> searchJob(JobSearch jobSearch, int page, int size) {	
		return jobRepo.search(queryFunc(jobSearch), countFunc(jobSearch), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<JobListItem>> queryFunc(JobSearch jobSearch) {
		return cb -> {
			var cq = cb.createQuery(JobListItem.class);
			var root = cq.from(Job.class);
			
			JobListItem.select(cq, root);
			cq.where(jobSearch.where(cb, root));
			cq.orderBy(cb.asc(root.get(Job_.createdAt)));
			
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(JobSearch jobSearch) {
		return cb -> {
			 var cq = cb.createQuery(Long.class);
			 var root = cq.from(Job.class);
			 
			 cq.select(cb.count(root.get(Job_.id)));
			 cq.where(jobSearch.where(cb, root));
			
			return cq;
		};
	}

}
