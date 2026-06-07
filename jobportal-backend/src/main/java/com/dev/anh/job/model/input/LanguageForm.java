package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotNull;

public record LanguageForm(
	@NotNull(message = "Please fill your platform")
	String name) {

}
