package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.QuestionOption;

public record QuizOption(
	Long optionId,
	String optionAnswer,
	Boolean isCorrect) {

	public static QuizOption from(QuestionOption entity) { 
		return new QuizOption(entity.getId(), entity.getOptionAnswer(), entity.getIsCorrect());
	}
}
