package com.dev.anh.job.model.input;

import jakarta.validation.constraints.NotNull;

public record QuizAnswerOptionList(
	@NotNull(message = "Please fill quiz option id.")
	Long optionId,
	Boolean isCorrect) {

}
