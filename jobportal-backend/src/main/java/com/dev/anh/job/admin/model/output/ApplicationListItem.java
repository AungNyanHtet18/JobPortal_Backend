package com.dev.anh.job.admin.model.output;

import com.dev.anh.job.model.consts.ApplicationStatus;
import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.Job_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;

public record ApplicationListItem(
	String jobTitle,
	ApplicationStatus status,
	String companyName,
	String applicantName,
	Gender gender) {

	public static void select(CriteriaQuery<ApplicationListItem> cq, Root<JobApply> root, Join<JobApply, Job> job, Join<JobApply, Applicant> applicant) {
		
		cq.multiselect(
		   job.get(Job_.career).get(Career_.roleName),
		   root.get(JobApply_.status),
		   job.get(Job_.company).get(Company_.account).get(Account_.name),
		   applicant.get(Applicant_.account).get(Account_.name),
		   applicant.get(Applicant_.gender));
	   }

}