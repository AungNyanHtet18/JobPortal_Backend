package com.dev.anh.job.model.input;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuizAnswerForm(
	@NotNull(message = "Please fill quiz id to answer.")
	Long quizId,
	@NotEmpty(message = "Please select at least one answer for quiz question.")
	List<@Valid QuizAnswerList> quizAnswerLists) {	
}
