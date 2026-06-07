package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.Language;

public record ApplicantLanguageDetails(
		String name) {

	public static ApplicantLanguageDetails from(Language language) {
		return new ApplicantLanguageDetails(language.getName());
	}
	
	
}
