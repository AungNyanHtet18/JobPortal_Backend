package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.ApplicantionStatus;
import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk_;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record JobApplicationListItem(
	Long applicantId,
	String  applicantName,
	String applicantEmail,
	Gender gender,
    ApplicantionStatus status) {

	public static void select(CriteriaQuery<JobApplicationListItem> cq, Root<JobApply> root) {
		
		var applicant = root.join(JobApply_.applicant, JoinType.INNER);
		
		cq.multiselect(
			root.get(JobApply_.id).get(JobApplyPk_.applicantId),
			applicant.get(Applicant_.account).get(Account_.name),
			applicant.get(Applicant_.account).get(Account_.email),
			applicant.get(Applicant_.gender),
			root.get(JobApply_.status));
		
	}

}
