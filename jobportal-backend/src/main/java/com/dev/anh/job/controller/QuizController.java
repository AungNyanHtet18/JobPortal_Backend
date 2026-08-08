package com.dev.anh.job.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dev.anh.job.model.input.QuizAnswerForm;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.output.QuizDetails;
import com.dev.anh.job.model.output.QuizTitleListItem;
import com.dev.anh.job.model.service.QuizService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("quiz")
@RequiredArgsConstructor
public class QuizController {

	private final QuizService quizService;

	@GetMapping("quiztitle")
	PageResult<QuizTitleListItem> searchQuizTitle(
			@RequestParam(required = false, defaultValue = "0") int page,
			@RequestParam(required = false,defaultValue = "16")int size) {
		return quizService.searchQuizTitle(page, size);
	}

	@PostMapping
	ModificationResult<Integer> answerQuiz(@RequestBody @Validated QuizAnswerForm form) {
		return quizService.answerQuiz(form);
	}

	@GetMapping("{id}")
	QuizDetails findQuizById(@PathVariable Long id) {
		return quizService.findByQuizId(id);
	}
}
