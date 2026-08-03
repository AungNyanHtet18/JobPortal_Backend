package com.dev.anh.job.admin.model.output;

import com.dev.anh.job.model.consts.QuestionType;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.QuestionOption_;
import com.dev.anh.job.model.entity.Quiz;
import com.dev.anh.job.model.entity.QuizQuestion;
import com.dev.anh.job.model.entity.QuizQuestion_;
import com.dev.anh.job.model.entity.Quiz_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record QuizListItem(
	Long id,
	String quizTitle,
	Integer passingScore,
	String roleName,
	Long questionId,
	String questionTitle,
	QuestionType questionType,
	Integer marks,
	Long questionOptionsCount) {

	public static void select(CriteriaQuery<QuizListItem> cq, CriteriaBuilder cb, Join<QuizQuestion, Quiz> quiz, Root<QuizQuestion> root) {

		var quizOption = root.join(QuizQuestion_.questionOptions, JoinType.LEFT);
		
		cq.multiselect(
		  quiz.get(Quiz_.id),
		  quiz.get(Quiz_.quizTitle),
		  quiz.get(Quiz_.passingScore),
		  quiz.get(Quiz_.career).get(Career_.roleName),
		  root.get(QuizQuestion_.id),
		  root.get(QuizQuestion_.questionTitle),
		  root.get(QuizQuestion_.questionType),
		  root.get(QuizQuestion_.marks),
		  cb.count(quizOption.get(QuestionOption_.id)));
		
		cq.groupBy(
		  quiz.get(Quiz_.id),
		  quiz.get(Quiz_.quizTitle),
		  quiz.get(Quiz_.passingScore),
		  quiz.get(Quiz_.career).get(Career_.roleName),
		  root.get(QuizQuestion_.id),
		  root.get(QuizQuestion_.questionTitle),
		  root.get(QuizQuestion_.questionType),
		  root.get(QuizQuestion_.marks));
		
	}

}
