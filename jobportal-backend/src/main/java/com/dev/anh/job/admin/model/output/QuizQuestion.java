package com.dev.anh.job.admin.model.output;

import java.util.List;

import com.dev.anh.job.model.consts.QuestionType;

public record QuizQuestion(
	String questionTitle,
	QuestionType questionType,
	Integer marks,
	List<QuizOption> quizOptions) {

	public static QuizQuestion from(com.dev.anh.job.model.entity.QuizQuestion entity) {
		var quizOptions = entity.getQuestionOptions().stream().map(QuizOption::from).toList();
		return new QuizQuestion(
				 entity.getQuestionTitle(), 
				 entity.getQuestionType(), 
				 entity.getMarks(), 
				 quizOptions);
	}
	
}
