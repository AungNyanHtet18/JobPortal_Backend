package com.dev.anh.job.model.output;

import java.time.LocalDate;

import com.dev.anh.job.model.consts.QualificationType;
import com.dev.anh.job.model.entity.Education;

public record ApplicantEducationDetails(
	QualificationType qualificationType,
	String qualificationName,
	LocalDate completionDate) {

	public static ApplicantEducationDetails from(Education education) {
		return new ApplicantEducationDetails(
				education.getQualificationType(), 
				education.getQualificationName(), 
				education.getCompletionDate());
	}
}
