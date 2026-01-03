package com.dev.anh.job.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dev.anh.job.model.input.ApplicantForm;
import com.dev.anh.job.model.output.ApplicantDetails;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.utils.exception.BusinessException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicantService {

	private final AccountRepo accountRepo;
	private final ApplicantRepo applicantRepo;
	
	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<Long> storeApplicantInfo(String username, ApplicantForm form) {
		
		var account = accountRepo.findOneByEmail(username)
						.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
		
			 if(StringUtils.hasLength(form.applicantName())) {
				  account.setName(form.applicantName());
				  accountRepo.saveAndFlush(account);
			 }
		
			 //Convert Skill List to String
			 String skills = String.join(",", form.skills());
			  
			 applicantRepo.save(form.entity(account, skills));
			return  new ModificationResult<Long>(account.getId());
		
		
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public ModificationResult<Long> updateApplicantInfo(Long id, ApplicantForm form) {
		
		var account = accountRepo.findById(id)
						 .orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));
		
		
		var applicant = applicantRepo.findById(id)
							.orElseThrow(() -> new BusinessException("Applicant with %s id is nod found".formatted(id)));
		
		 if(StringUtils.hasLength(form.applicantName())) {
			  account.setName(form.applicantName());
			  accountRepo.saveAndFlush(account);
		 }
			
		 //Convert Skill List to String
		 String skills = String.join(",", form.skills());
		 
		applicant.setAccount(account);
		applicant.setGender(form.gender());
		applicant.setHighestEducationalAttainment(form.highestEducationalAttainment());
		applicant.setResume(form.resume());
		applicant.setSkills(skills);
		applicant.setCurrentJob(form.currentJob());
		applicant.setProfessionalSummary(form.professionalSummary());
		applicant.setContactDetail(form.contactDetail());
		applicant.setAddress(form.address());
		
	    applicantRepo.save(applicant);

		return new ModificationResult<Long>(id);
	}

	public ApplicantDetails findById(Long id) {
		return applicantRepo.findById(id).map(a -> ApplicantDetails.from(a)).orElseThrow(() -> new BusinessException("Applicant with %d is not found".formatted(id)));
	}

	@Transactional
	@PreAuthorize("hasAuthority('Applicant') and #username eq authentication.name")
	public String uploadImages(String username, String uploadPath, MultipartFile file) {
		
		var applicant = applicantRepo.findByEmail(username).orElseThrow(() -> new BusinessException("Firstly,fill applicant infomation before uploading profile image "));
		
		try {
		var profileImageName = getProfileImageName(username, file);
		var profileImagePath = Path.of(uploadPath, profileImageName);

		
		if(!Files.exists(profileImagePath)) {
			 Files.createDirectories(profileImagePath);
		}
		
		
		//Save the file 
		Files.copy(file.getInputStream(), profileImagePath, StandardCopyOption.REPLACE_EXISTING);
		applicant.setProfilePhoto(profileImageName);
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return "uploaded succesfully";

	}

	private String getProfileImageName(String username, MultipartFile file) {
		
		if(file.isEmpty()) {
			 throw new BusinessException("File is empty");
		}
		
		var fileName = file.getOriginalFilename(); //Retrieving original file name 
		var array = fileName.split("\\."); //Split with .(dot) 
		var extension = array[array.length -1]; //Retrieving latest extension(eg.jpg)
		
		return "%s.%s".formatted(username,extension);
	} 

	
}
