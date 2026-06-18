package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.ApplicantionStatus;
import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.Job_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record ApplicantAppliedJobListItem(
	String positionName,
	Double maxSalary,
	Double minSalary,
	Long jobId,
	JobType jobType,
	JobLevel jobLevel,
	String companyName,
	String clientName,
	String jobLocation,
	String websiteUrl,
	ApplicantionStatus status) {

	public static void select(CriteriaQuery<ApplicantAppliedJobListItem> cq, Root<JobApply> root) {
		
		var job = root.join(JobApply_.job, JoinType.INNER);
		
		cq.multiselect(
			job.get(Job_.career).get(Career_.roleName),
			job.get(Job_.maxSalaryRange),
			job.get(Job_.minSalaryRange),
			job.get(Job_.id),
			job.get(Job_.jobType),
			job.get(Job_.jobLevel),
			job.get(Job_.company).get(Company_.account).get(Account_.name),
			job.get(Job_.clientName),
			job.get(Job_.location),
			job.get(Job_.company).get(Company_.websiteUrl),
			root.get(JobApply_.status));
	}

}
