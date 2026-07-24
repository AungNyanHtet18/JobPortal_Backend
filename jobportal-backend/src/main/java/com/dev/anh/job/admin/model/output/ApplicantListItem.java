package com.dev.anh.job.admin.model.output;

import java.time.LocalDateTime;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record ApplicantListItem(
	Long id,
	String name,
	String email,
	Boolean active,
	LocalDateTime activatedAt,
	String profilePhoto,
	Gender gender,
	Long jobApplicationCount) {

	public static void select(CriteriaQuery<ApplicantListItem> cq, CriteriaBuilder cb, Root<Applicant> root, Join<Applicant, Account> account) {
		
		var jobApply = root.join(Applicant_.jobApply,JoinType.LEFT);
		
		cq.multiselect(
		  root.get(Applicant_.id),
		  account.get(Account_.name),
		  account.get(Account_.email),
		  account.get(Account_.active),
		  account.get(Account_.activatedAt),
		  root.get(Applicant_.profilePhoto),
		  root.get(Applicant_.gender),
		  cb.count(jobApply.get(JobApply_.id).get(JobApplyPk_.applicantId)));
		
		cq.groupBy(
		  root.get(Applicant_.id),
		  account.get(Account_.name),
		  account.get(Account_.email),
		  account.get(Account_.active),
		  account.get(Account_.activatedAt),
		  root.get(Applicant_.profilePhoto),
		  root.get(Applicant_.gender)	
		);	
	}

}
