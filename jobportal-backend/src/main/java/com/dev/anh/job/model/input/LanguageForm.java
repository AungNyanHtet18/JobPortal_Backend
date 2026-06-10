package com.dev.anh.job.model.input;

import com.dev.anh.job.model.consts.LanguageLevel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record LanguageForm(
	@NotBlank(message = "Please fill your platform")
	String languageName,
	@NotNull(message = "Please enter your language level.")
	LanguageLevel languageLevel) {
}
