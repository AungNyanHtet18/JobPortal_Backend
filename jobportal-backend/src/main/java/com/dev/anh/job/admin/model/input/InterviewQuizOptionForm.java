package com.dev.anh.job.admin.model.input;

import com.dev.anh.job.model.entity.QuestionOption;
import com.dev.anh.job.model.entity.QuizQuestion;

import jakarta.validation.constraints.NotBlank;

public record InterviewQuizOptionForm(
	@NotBlank(message = "Please fill option answer for quiz.")	
	String optionAnswer,
	Boolean isCorrect) {

	public static QuestionOption entity(QuizQuestion quizQuestion, InterviewQuizOptionForm form) {
		 var questionOption = new QuestionOption();
		 questionOption.setQuizQuestion(quizQuestion);
		 questionOption.setOptionAnswer(form.optionAnswer());
		 questionOption.setIsCorrect(form.isCorrect());
		 
		 return questionOption;
	}
	
	
}
