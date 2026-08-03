package com.dev.anh.job.admin.model.output;

import java.util.List;

import com.dev.anh.job.model.entity.Quiz;

public record QuizDetails(
	Long id,
	Long roleId,
	String quizTitle,
	Integer passingScore,
	List<QuizQuestion> quizQuestions) {

	public static QuizDetails from(Quiz entity) {
		var quizQuestions = entity.getQuizQuestions().stream().map(QuizQuestion::from).toList();
		
		return new QuizDetails(
			entity.getId(),
			entity.getCareer().getId(), 
			entity.getQuizTitle(), 
			entity.getPassingScore(), 
			quizQuestions);
	}
	
}