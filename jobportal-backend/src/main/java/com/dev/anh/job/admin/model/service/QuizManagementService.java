package com.dev.anh.job.admin.model.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.admin.model.input.InterviewQuizOptionForm;
import com.dev.anh.job.admin.model.input.InterviewQuizQuestionForm;
import com.dev.anh.job.admin.model.input.QuizForm;
import com.dev.anh.job.model.entity.QuestionOption;
import com.dev.anh.job.model.entity.QuizQuestion;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.CareerRepo;
import com.dev.anh.job.model.repo.QuizRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizManagementService {

	private final AccountRepo accountRepo;
	private final CareerRepo careerRepo;
	private final QuizRepo quizRepo;
	
	@Transactional
	@PreAuthorize("hasAuthority('Admin') and #username eq authentication.name")
	public ModificationResult<Long> createQuiz(String username, QuizForm form) {
		
		var account = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Account with %s was not found".formatted(username)));
		
		var career = careerRepo.findById(form.roleId())
				.orElseThrow(() -> new BusinessException("Career with ID %d was not found".formatted(form.roleId())));
		
		var quiz = form.entity(account, career);
		
		List<QuizQuestion> quizQuestions = form.interviewQuizQuestionForms()
			   .stream()
		       .map(questionForm -> {
			    	 var quizQuestion =  InterviewQuizQuestionForm.entity(quiz, questionForm);
			    	 
			    	 List<QuestionOption> options = questionForm.interviewQuizOptionForms()
						 .stream()
						 .map(optionForm -> InterviewQuizOptionForm.entity(quizQuestion, optionForm) )
						 .collect(Collectors.toList());
			    	 
			    	 quizQuestion.setQuestionOptions(options);
			    	 return quizQuestion;
		       })
		       .collect(Collectors.toList());

		 quiz.setQuizQuestions(quizQuestions);
		 quizRepo.save(quiz);
		 
		return new ModificationResult<Long>(quiz.getId());
	}
	
	@Transactional
	@PreAuthorize("hasAuthority('Admin')")
	public ModificationResult<Long> updateQuiz(Long id, QuizForm form) {
		
		var quiz = quizRepo.findById(id)
						.orElseThrow(() -> new BusinessException("Quiz with ID %d is not found".formatted(id)));
		
		var career = careerRepo.findById(form.roleId())
				.orElseThrow(() -> new BusinessException("Career with ID %d was not found".formatted(form.roleId())));
		
		quiz.setCareer(career);
		quiz.setQuizTitle(form.quizTitle());
		quiz.setPassingScore(form.passingScore());
		
		List<QuizQuestion> quizQuestions = form.interviewQuizQuestionForms()
			   .stream()
		       .map(questionForm -> {
			    	 var quizQuestion =  InterviewQuizQuestionForm.entity(quiz, questionForm);
			    	 
			    	 List<QuestionOption> options = questionForm.interviewQuizOptionForms()
						 .stream()
						 .map(optionForm -> InterviewQuizOptionForm.entity(quizQuestion, optionForm) )
						 .collect(Collectors.toList());
			    	 
			    	 quizQuestion.setQuestionOptions(options);
			    	 return quizQuestion;
		       })
		       .collect(Collectors.toList());
		
		quiz.getQuizQuestions().clear();
		quiz.getQuizQuestions().addAll(quizQuestions);
		quizRepo.save(quiz);
		
		return new ModificationResult<Long>(id);
	}
}
