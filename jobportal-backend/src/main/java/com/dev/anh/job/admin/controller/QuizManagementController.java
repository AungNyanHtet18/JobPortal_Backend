package com.dev.anh.job.admin.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.admin.model.input.QuizForm;
import com.dev.anh.job.admin.model.service.QuizManagementService;
import com.dev.anh.job.model.output.ModificationResult;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("admin")
@RequiredArgsConstructor
public class QuizManagementController {

	private final QuizManagementService quizManagementService;
	
	@PostMapping("quiz")
	ModificationResult<Long> createQuiz(@RequestBody @Validated QuizForm form) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 return quizManagementService.createQuiz(username, form);
	}
	
	@PutMapping("quiz/{id}")
	ModificationResult<Long> updateQuiz(
			@PathVariable Long id,
			@RequestBody @Validated QuizForm form) {
		return quizManagementService.updateQuiz(id, form); 
	}
}
