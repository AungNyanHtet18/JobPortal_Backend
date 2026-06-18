package com.dev.anh.job.model.output;

import java.time.LocalDateTime;
import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record JobListItem(
	  Long jobId,
	  String positionName,
	  Double maxSalaryRange,
	  Double minSalaryRange,
	  JobLevel jobLevel,
	  JobType jobType,
	  String companyName,
	  String clientName,
	  String jobLocation,
	  String profilePhoto,
	  LocalDateTime createAt) {
	
	public static void select(CriteriaQuery<JobListItem> cq, Root<Job> root, Join<Job, Company> company) {
		
	   var account =  company.join(Company_.account, JoinType.INNER);
		
		cq.multiselect(
			root.get(Job_.id),
			root.get(Job_.career).get(Career_.roleName),
			root.get(Job_.maxSalaryRange),
			root.get(Job_.minSalaryRange),
			root.get(Job_.jobLevel),
			root.get(Job_.jobType),
			account.get(Account_.name),
			root.get(Job_.clientName),
			root.get(Job_.location),
			company.get(Company_.profilePhoto),
			root.get(Job_.createAt));
	}

}
