package com.dev.anh.job.model.input;

import java.time.LocalDate;

import com.dev.anh.job.model.consts.QualificationType;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Education;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record EducationForm(
	@NotNull(message = "Please enter qualification type.")
	QualificationType qualificationType,	
	@NotBlank(message = "Please fill your qualification name.")
	String qualificationName,
	@NotNull(message = "Please enter completion date.")
	LocalDate completionDate) {
	
	public static Education ApplicantEducation(Applicant applicant, EducationForm form) {
		 var education = new Education();
		 education.setApplicant(applicant);
		 education.setQualificationType(form.qualificationType());
		 education.setQualificationName(form.qualificationName());
		 education.setCompletionDate(form.completionDate());
		 return education;
	}
	
}
