package com.dev.anh.job.admin.model.output;

import com.dev.anh.job.model.entity.QuestionOption;

public record QuizOption(
	String optionAnswer,
	Boolean isCorrect) {

	public static QuizOption from(QuestionOption entity) { 
		return new QuizOption(entity.getOptionAnswer(), entity.getIsCorrect());
	}
}
