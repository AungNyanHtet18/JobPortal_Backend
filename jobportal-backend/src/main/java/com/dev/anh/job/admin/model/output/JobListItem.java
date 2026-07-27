package com.dev.anh.job.admin.model.output;

import java.time.LocalDateTime;
import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record JobListItem(
	Long id,
	String jobName,
	String companyName,
	String clientName,
	JobLevel jobLevel,
	JobType jobType,
	Double minSalaryRange,
	Double maxSalaryRange,
	boolean deleted,
	LocalDateTime createdAt) {

	public static void select(CriteriaQuery<JobListItem> cq, Root<Job> root) {
		
		cq.multiselect(
		   root.get(Job_.id),
		   root.get(Job_.career).get(Career_.roleName),
		   root.get(Job_.company).get(Company_.account).get(Account_.name),
		   root.get(Job_.clientName),
		   root.get(Job_.jobLevel),
		   root.get(Job_.jobType),
		   root.get(Job_.minSalaryRange),
		   root.get(Job_.maxSalaryRange),
		   root.get(Job_.deleted),
		   root.get(Job_.createdAt)); 
	}

}
