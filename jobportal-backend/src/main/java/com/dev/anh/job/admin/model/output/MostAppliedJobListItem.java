package com.dev.anh.job.admin.model.output;

import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.JobApply_;
import com.dev.anh.job.model.entity.Job_;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record MostAppliedJobListItem(
	Long jobId,
	String jobTitle,
	Long totalApplications) {

	public static void select(CriteriaQuery<MostAppliedJobListItem> cq, CriteriaBuilder cb,  Root<Job> root) {

		var jobApply = root.join(Job_.jobApply, JoinType.LEFT);
		
		var totalApplications = cb.count(jobApply.get(JobApply_.id).get(JobApplyPk_.jobId));
		
		cq.multiselect(
		  root.get(Job_.id),
		  root.get(Job_.career).get(Career_.roleName),
		  totalApplications);

		cq.groupBy(
		  root.get(Job_.id),
		  root.get(Job_.career).get(Career_.roleName));
		
		cq.orderBy(cb.desc(totalApplications));
		
	}

}
