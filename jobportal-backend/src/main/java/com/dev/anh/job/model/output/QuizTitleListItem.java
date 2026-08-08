package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Quiz;
import com.dev.anh.job.model.entity.QuizQuestion_;
import com.dev.anh.job.model.entity.Quiz_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

public record QuizTitleListItem(
	Long quizId,
	String quizTitle,
	String roleName,
	Long quizQuestionCount) {

	public static void select(CriteriaQuery<QuizTitleListItem> cq, CriteriaBuilder cb, Root<Quiz> root) {

		var quizQuestion = root.join(Quiz_.quizQuestions, JoinType.LEFT);
		
		cq.multiselect(
			root.get(Quiz_.id),
			root.get(Quiz_.quizTitle),
			root.get(Quiz_.career).get(Career_.roleName),
			cb.count(quizQuestion.get(QuizQuestion_.id))
		);
		
		cq.groupBy(
			root.get(Quiz_.id),
			root.get(Quiz_.quizTitle),
			root.get(Quiz_.career).get(Career_.roleName)
		);
		
		cq.orderBy(cb.asc(root.get(Quiz_.id)));
		
	}

}
