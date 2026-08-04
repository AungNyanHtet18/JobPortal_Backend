package com.dev.anh.job.model.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.model.entity.QuestionOption;
import com.dev.anh.job.model.entity.QuizQuestion;
import com.dev.anh.job.model.input.QuizAnswerForm;
import com.dev.anh.job.model.input.QuizAnswerList;
import com.dev.anh.job.model.input.QuizAnswerOptionList;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.QuizDetails;
import com.dev.anh.job.model.repo.QuizRepo;
import com.dev.anh.job.utils.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class QuizService {

	private final QuizRepo quizRepo;
	
    @Transactional
	public ModificationResult<Integer> answerQuiz(QuizAnswerForm form) {
    	
    	var quiz = quizRepo.findById(form.quizId())
    				 .orElseThrow(() -> new BusinessException("Quiz Id with %d was not found".formatted(form.quizId())));
    	
    	List<QuizQuestion> quizQuestions = quiz.getQuizQuestions();
    	
    	var totalMarks = 0;
    	
    	for(var question = 0; question < quizQuestions.size(); question ++) {
    		 QuizQuestion dbQuestion = quizQuestions.get(question);
    		 QuizAnswerList submittedAnswer = form.quizAnswerLists().get(question);
    		 
    		 if(dbQuestion.getId() == submittedAnswer.questionId()) {
    			 
    			 List<QuestionOption> dbQuestionOptions = dbQuestion.getQuestionOptions();
    			 var answerIsCorrect = 0;
    			 
    			 for(var quizOption = 0; quizOption < dbQuestionOptions.size(); quizOption++) {
    				  QuestionOption dbQuestionOption = dbQuestionOptions.get(quizOption);
    				  QuizAnswerOptionList submittedAnswerOption =  submittedAnswer.answerOptions().get(quizOption);
    				  
    				  if(dbQuestionOption.getIsCorrect() == submittedAnswerOption.isCorrect()) {
    					   answerIsCorrect ++;
    				  }
    			 }
    			 
    			 if(answerIsCorrect == dbQuestionOptions.size()) {
    				  totalMarks += dbQuestion.getMarks();
    			 }
    		 }
    	}
		
		return new ModificationResult<Integer>(totalMarks);
	}
	
	public QuizDetails findByQuizId(Long id) {
		return quizRepo.findById(id).map(QuizDetails::from)
				 .orElseThrow(() -> new BusinessException("Quiz with %d is not found".formatted(id)));
	}	
}
