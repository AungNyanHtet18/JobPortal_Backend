package com.dev.anh.job.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record JobSearch(
	JobLevel jobLevel,
	JobType jobType,
	Boolean deleted,
	String keyword) {

	public Predicate[]  where(CriteriaBuilder cb, Root<Job> root, Join<Job, Company> company) {
		
		var param = new ArrayList<Predicate>();
		
		if(null != jobLevel) {
			 param.add(cb.equal(root.get(Job_.jobLevel), jobLevel));
		}
		
		if(null != jobType) {
			 param.add(cb.equal(root.get(Job_.jobType), jobType));
		}
		
		if(null != deleted) {
			 param.add(cb.equal(root.get(Job_.deleted), deleted));
		}
		
		if(StringUtils.hasLength(keyword)) {
			 param.add(cb.or(
					cb.like(cb.lower(root.get(Job_.positionName)), keyword.toLowerCase().concat("%")),
					cb.like(cb.lower(company.get(Company_.location)), keyword.toLowerCase().concat("%")),
					cb.like(cb.lower(company.get(Company_.account).get(Account_.name)), keyword.toLowerCase().concat("%"))));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}

}
