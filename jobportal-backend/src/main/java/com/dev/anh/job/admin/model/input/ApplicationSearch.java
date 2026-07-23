package com.dev.anh.job.admin.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.JobApply;
import com.dev.anh.job.model.entity.Job_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record ApplicationSearch(
	String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<JobApply> root, Join<JobApply, Job> job,
			Join<JobApply, Applicant> applicant) {

		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
			 param.add(cb.or(
				cb.like(cb.lower(job.get(Job_.career).get(Career_.roleName)), keyword.toLowerCase().concat("%")),
				cb.like(cb.lower(job.get(Job_.company).get(Company_.account).get(Account_.name)) , keyword.toLowerCase().concat("%")),
				cb.like(cb.lower(applicant.get(Applicant_.account).get(Account_.name)), keyword.toLowerCase().concat("%"))
			  ));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}

}
