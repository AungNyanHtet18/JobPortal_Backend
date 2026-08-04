package com.dev.anh.job.model.input;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuizAnswerList(
	@NotNull(message = "Please fill question id for quiz.")	
	Long questionId,
	@NotEmpty(message = "Please choose at least one option for quiz question.")
	List<QuizAnswerOptionList> answerOptions) {

}
