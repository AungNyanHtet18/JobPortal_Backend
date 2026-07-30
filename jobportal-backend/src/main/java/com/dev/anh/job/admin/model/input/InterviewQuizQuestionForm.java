package com.dev.anh.job.admin.model.input;

import java.util.List;

import com.dev.anh.job.model.consts.QuestionType;
import com.dev.anh.job.model.entity.Quiz;
import com.dev.anh.job.model.entity.QuizQuestion;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record InterviewQuizQuestionForm(
	@NotBlank(message = "Please fill question title for quiz.")
	String questionTitle,
	@NotNull(message = "Please select question title for quiz.")
	QuestionType questionType,
	@NotNull(message = "Please fill marks for quiz.")
	@Min(value = 1, message = "Marks must be greater than 0.")
	Integer marks,
	@NotEmpty(message = "Please select at least one quiz option.")
	List<@Valid InterviewQuizOptionForm> interviewQuizOptionForms) {

	public static QuizQuestion entity(Quiz quiz, InterviewQuizQuestionForm form) {
		var quizQuestion = new QuizQuestion(); 
		quizQuestion.setQuiz(quiz);
		quizQuestion.setQuestionTitle(form.questionTitle());
		quizQuestion.setQuestionType(form.questionType());
		quizQuestion.setMarks(form.marks());
		return quizQuestion;
	}

}
