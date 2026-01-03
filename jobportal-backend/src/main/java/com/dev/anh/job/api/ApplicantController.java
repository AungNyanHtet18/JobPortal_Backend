package com.dev.anh.job.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.output.ApplicantDetails;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.service.ApplicantService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("applicant")
@RequiredArgsConstructor
public class ApplicantController {

	private final ApplicantService service;
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	@GetMapping("{id}")
	ApplicantDetails findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@PostMapping
	ModificationResult<Long> storeApplicantInfo(@RequestBody @Validated ApplicantForm form) {
		var username = SecurityContextHolder.getContext().getAuthentication().getName(); 
		return service.storeApplicantInfo(username, form);
	}
	
	@PutMapping("{id}")
	ModificationResult<Long> updateApplicantInfo(@PathVariable Long id,
					@RequestBody @Validated ApplicantForm form) {
		return service.updateApplicantInfo(id, form);
	}
	
	@PatchMapping(value= "photo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	String uploadApplicant(@RequestParam MultipartFile file) {
		
		var username = SecurityContextHolder.getContext().getAuthentication().getName();		
		return service.uploadImages(username, uploadPath, file);
	}

	

}
