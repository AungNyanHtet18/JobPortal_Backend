package com.dev.anh.job.model.service;


import java.io.IOException;
import java.util.Set;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.anh.job.model.input.CompanyForm;
import com.dev.anh.job.model.output.CompanyDetails;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.CompanyRepo;
import com.dev.anh.job.utils.FileProvider;
import com.dev.anh.job.utils.exception.BusinessException;
import com.dev.anh.job.utils.exception.FileInvalidException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CompanyService {

	private final AccountRepo accountRepo;
	private final CompanyRepo companyRepo;
	private final FileProvider fileProvider;
	
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount') and #username eq authentication.name")
	public ModificationResult<Long> storeCompanyInfo(String username, CompanyForm form) {
		
		var account = accountRepo.findOneByEmail(username)
							.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
		
		
		if(StringUtils.hasLength(form.companyName())) {
			 account.setName(form.companyName());
			 accountRepo.saveAndFlush(account);
		}
		
		companyRepo.save(form.entity(account));
		
		return new ModificationResult<Long>(account.getId());
	}
	 
	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> updateCompanyInfo(Long id, CompanyForm form) {
		
		var account = accountRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));
							 
		var company = companyRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Company with %s id is not found".formatted(id)));
		
		
		if(StringUtils.hasLength(form.companyName())) {
			account.setName(form.companyName()); 
			accountRepo.saveAndFlush(account);
		}
		
		company.setAccount(account);
		company.setLocation(form.location());
		company.setPhone(form.phone());
		company.setWebsiteUrl(form.websiteUrl());
		company.setDescription(form.description());
	
		companyRepo.save(company);
		
		return new ModificationResult<Long>(id);
	}
	
	public CompanyDetails findByName(String email) {
		 return companyRepo.findByEmail(email).map(a -> CompanyDetails.from(a)).orElse(null);
	}

	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount') and #username eq authentication.name")
	public ModificationResult<String> uploadCompanyProfile(String username, String uploadPath, MultipartFile file) {
		fileProvider.validateFile(file, Set.of("png", "jpg", "jpeg")); //validating file

		var company = companyRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Firstly,fill company information before uploading profile image "));

		try {
			var profileImageName = fileProvider.saveFile(uploadPath, company.getAccount().getName(), file);
			company.setProfilePhoto(profileImageName);

			return new ModificationResult<String>("Successfully Uploaded Profile Photo" + profileImageName);

		} catch (IOException e) {
			throw new FileInvalidException("Invalid Profile Upload", e);
		}
	}
	

}
