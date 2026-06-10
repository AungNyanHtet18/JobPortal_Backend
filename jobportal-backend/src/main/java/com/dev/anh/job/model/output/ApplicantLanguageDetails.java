package com.dev.anh.job.model.output;

import com.dev.anh.job.model.consts.LanguageLevel;
import com.dev.anh.job.model.entity.Language;

public record ApplicantLanguageDetails(
		String languageName,
		LanguageLevel languageLevel) {

	public static ApplicantLanguageDetails from(Language language) {
		return new ApplicantLanguageDetails(language.getLanguageName(), language.getLanguageLevel());
	}
	
	
}
