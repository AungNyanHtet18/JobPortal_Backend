package com.dev.anh.job.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	@GetMapping("{id}")
	CompanyDetails findById(@PathVariable Long id) {
		 return service.findById(id);
	}
	
	@PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long>storeCompanyInfo(
						@RequestPart("form") @Validated CompanyForm form,
						@RequestPart(value ="file", required = false) MultipartFile file) {
		 var username = SecurityContextHolder.getContext().getAuthentication().getName();
		 var result = service.storeCompanyInfo(username, form);
		 
		 if(file != null && !file.isEmpty()) {
			  service.uploadCompanyProfile(username, uploadPath.concat("/companyprofile"), file);
		 }		  
		 
		  return result;
	}
	
	@PutMapping(value = "{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ModificationResult<Long>updateCompanyRepo(
			@PathVariable Long id,
			@RequestPart("form") @Validated CompanyForm form,
			@RequestPart(value = "file", required = false) MultipartFile file) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		var result = service.updateCompanyInfo(id, form);
		
		 if(file != null && !file.isEmpty()) {
			  service.uploadCompanyProfile(username, uploadPath.concat("/companyprofile"), file);
		 }		
		
		return result;
		
		 
	}
}
