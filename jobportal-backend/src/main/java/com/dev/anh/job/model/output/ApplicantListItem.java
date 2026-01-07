package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Applicant;

import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record ApplicantListItem(
	String name,
	Gender gender,
	String skills,
	String highestEducationalAttainment,
	String profilePhoto) {

	public static void select(CriteriaQuery<ApplicantListItem> cq, Root<Applicant> root) {
		// TODO Auto-generated method stub
		
	}

}
