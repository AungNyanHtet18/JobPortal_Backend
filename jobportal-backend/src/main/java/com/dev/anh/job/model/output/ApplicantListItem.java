package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.Gender;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public record ApplicantListItem(
	String name,
	Gender gender,
	String skills,
	String highestEducationalAttainment,
	String profilePhoto) {

	public static void select(CriteriaQuery<ApplicantListItem> cq, Root<Applicant> root) {
		
		cq.multiselect(
			root.get(Applicant_.account).get(Account_.name),
			root.get(Applicant_.gender),
			root.get(Applicant_.skills),
			root.get(Applicant_.highestEducationalAttainment),
			root.get(Applicant_.profilePhoto));
		
		
	}

}
