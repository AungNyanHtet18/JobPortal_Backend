package com.dev.anh.job.api;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.anh.job.model.input.CompanyForm;
import com.dev.anh.job.model.output.CompanyDetails;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.CompanyService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("company")
@RequiredArgsConstructor
public class CompanyController {

	private final CompanyService service;
	
	@GetMapping("{id}")
	CompanyDetails findById(@PathVariable Long id) {
		 return service.findById(id);
	}
	
	@PostMapping
	ModificationResult<Long>storeCompanyInfo(@RequestBody @Validated CompanyForm form) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 return service.storeCompanyInfo(username, form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Long>updateCompanyRepo(@PathVariable Long id,
			@RequestBody @Validated CompanyForm form) {
		return service.updateCompanyInfo(id, form);
	}
}
