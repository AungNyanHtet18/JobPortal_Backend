package com.dev.anh.job.model.output;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Account_;
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
	  Double salary,
	  JobLevel jobLevel,
	  JobType jobType,
	  String companyName,
	  String location,
	  LocalDateTime createAt) {

	
	public static void select(CriteriaQuery<JobListItem> cq, Root<Job> root, Join<Job, Company> company) {
		
	   var account =  company.join(Company_.account,JoinType.INNER);
		
		cq.multiselect(
			root.get(Job_.id),
			root.get(Job_.positionName),
			root.get(Job_.salary),
			root.get(Job_.jobLevel),
			root.get(Job_.jobType),
			account.get(Account_.name),
			company.get(Company_.location),
			root.get(Job_.createAt));
	}

}
