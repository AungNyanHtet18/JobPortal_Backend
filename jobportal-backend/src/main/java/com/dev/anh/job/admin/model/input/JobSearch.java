package com.dev.anh.job.admin.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record JobSearch(
	String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Root<Job> root) {
		
		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
			param.add(cb.or(
			  cb.like(cb.lower(root.get(Job_.company).get(Company_.account).get(Account_.name)), keyword.toLowerCase().concat("%")),
			  cb.like(cb.lower(root.get(Job_.career).get(Career_.roleName)), keyword.toLowerCase().concat("%")),
			  cb.like(cb.lower(root.get(Job_.clientName)), keyword.toLowerCase().concat("%")),
			  cb.like(cb.lower(root.get(Job_.location)), keyword.toLowerCase().concat("%"))
			));
		}
		
		return param.toArray(size -> new Predicate[size]);
	}
}
