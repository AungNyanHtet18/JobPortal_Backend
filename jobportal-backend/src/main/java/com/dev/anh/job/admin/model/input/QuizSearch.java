package com.dev.anh.job.admin.model.input;

import java.util.ArrayList;

import org.springframework.util.StringUtils;

import com.dev.anh.job.model.consts.QuestionType;
import com.dev.anh.job.model.entity.Career_;
import com.dev.anh.job.model.entity.Quiz;
import com.dev.anh.job.model.entity.QuizQuestion;
import com.dev.anh.job.model.entity.QuizQuestion_;
import com.dev.anh.job.model.entity.Quiz_;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public record QuizSearch(
	QuestionType questionType,
	String keyword) {

	public Predicate[] where(CriteriaBuilder cb, Join<QuizQuestion, Quiz> quiz, Root<QuizQuestion> root) {
		var param = new ArrayList<Predicate>();
		
		if(null != questionType) {
			param.add(cb.equal(root.get(QuizQuestion_.questionType), questionType));
		}
		
		if(StringUtils.hasLength(keyword)) {
			param.add(cb.or(
			   cb.like(cb.lower(quiz.get(Quiz_.quizTitle)), keyword.toLowerCase().concat("%")),
			   cb.like(cb.lower(quiz.get(Quiz_.career).get(Career_.roleName)) , keyword.toLowerCase().concat("%")),
			   cb.like(cb.lower(root.get(QuizQuestion_.questionTitle)), keyword.toLowerCase().concat("%"))
			));
		}

		return param.toArray(size -> new Predicate[size]);
	}

}
