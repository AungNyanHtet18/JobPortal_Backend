package com.dev.anh.job.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Education_;
import com.dev.anh.job.model.entity.Experience_;
import com.dev.anh.job.model.entity.Skill_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record ApplicantSearch(
	String keyword,
	Gender gender,
	Boolean deleted) {

	public  Predicate[]  where(CriteriaBuilder cb, Root<Applicant> root) {
		
		var skill = root.join(Applicant_.skills,JoinType.LEFT);
		var education = root.join(Applicant_.educations,JoinType.LEFT);
		var experience = root.join(Applicant_.experiences, JoinType.LEFT);
		
		var param = new ArrayList<Predicate>();
		
		if(StringUtils.hasLength(keyword)) {
			 param.add(cb.or(
				   cb.like(cb.lower(skill.get(Skill_.skillName)), keyword.toLowerCase().concat("%")),
				   cb.like(cb.lower(education.get(Education_.qualificationName)), keyword.toLowerCase().concat("%")),
				   cb.like(cb.lower(experience.get(Experience_.position)), keyword.toLowerCase().concat("%")),
				   cb.like(cb.lower(root.get(Applicant_.address)), keyword.toLowerCase().concat("%"))
				));
			 
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
