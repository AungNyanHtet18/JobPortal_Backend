package com.dev.anh.job.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record ApplicantSearch(
	String keyword,
	Gender gender ,
	Boolean deleted) {

	public  Predicate[]  where(CriteriaBuilder cb, Root<Applicant> root) {
		
		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
			 param.add(cb.or(
				   cb.like(cb.lower(root.get(Applicant_.skills)), keyword.toLowerCase().concat("%")),
				   cb.like(cb.lower(root.get(Applicant_.highestEducationalAttainment)), keyword.toLowerCase().concat("%"))));
			 
		}
		
		if(null != gender) {
			 param.add(cb.equal(root.get(Applicant_.gender), gender));
		}
		
		
		if(null != deleted) {
			 param.add(cb.equal(root.get(Applicant_.deleted), deleted));
		}

		return param.toArray(size -> new Predicate[size]);
	}

}
