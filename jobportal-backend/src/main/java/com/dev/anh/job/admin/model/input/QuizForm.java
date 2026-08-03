package com.dev.anh.job.admin.model.input;

import java.util.List;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Career;
import com.dev.anh.job.model.entity.Quiz;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record QuizForm(
	@NotNull(message = "Please fill role id for quiz.")
    Long roleId,
    @NotBlank(message = "Please fill question title for quiz.")
	String quizTitle,
	@NotNull(message = "Please fill passing score for quiz.")
	Integer passingScore,
	@NotEmpty(message = "Please select at least one quiz.")
	List<@Valid InterviewQuizQuestionForm> interviewQuizQuestions) {

	public Quiz entity(Account account, Career career) {
		var quiz = new Quiz();
		quiz.setCareer(career);
		quiz.setAccount(account);
		quiz.setQuizTitle(quizTitle);
		quiz.setPassingScore(passingScore);
		
		return quiz;
	}
	
	
}
