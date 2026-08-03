package com.dev.anh.job.model.service;

import java.io.IOException;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
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

	@Value("${app.upload.path}")
	private String uploadPath;

	public CompanyDetails findByCompanyId(Long id) {
		return companyRepo.findById(id).map(CompanyDetails::from)
				.orElseThrow(() -> new BusinessException("Applicant with %d is not found".formatted(id)));
	}

	public CompanyDetails findByCompanyName(String email) {
		return companyRepo.findByEmail(email).map(CompanyDetails::from).orElse(null);
	}

	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount') and #username eq authentication.name")
	public ModificationResult<Long> createCompany(String username, CompanyForm form, MultipartFile file) {

		var account = accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));

		// Specify active is true in order to display applicant profile
		account.setRoleStatus(true);

		if (StringUtils.hasLength(form.companyName())) {
			account.setName(form.companyName());
			accountRepo.save(account);
		}

		companyRepo.save(form.entity(account));

		if (file != null && !file.isEmpty()) {
			uploadCompanyProfile(username, uploadPath.concat("/companyprofile"), file);
		}

		return new ModificationResult<Long>(account.getId());
	}

	@Transactional
	@PreAuthorize("hasAuthority('CompanyAccount')")
	public ModificationResult<Long> updateCompany(Long id, CompanyForm form, MultipartFile file) {

		var account = accountRepo.findById(id)
				.orElseThrow(() -> new BusinessException("Account with %s id is not found".formatted(id)));

		var company = companyRepo.findById(id)
				.orElseThrow(() -> new BusinessException("Company with %s id is not found".formatted(id)));

		if (StringUtils.hasLength(form.companyName())) {
			account.setName(form.companyName());
			accountRepo.save(account);
		}

		company.setAccount(account);
		company.setIndustryType(form.industryType());
		company.setLocation(form.location());
		company.setPhone(form.phone());
		company.setWebsiteUrl(form.websiteUrl());
		company.setDescription(form.description());

		companyRepo.save(company);

		if (file != null && !file.isEmpty()) {
			uploadCompanyProfile(account.getEmail(), uploadPath.concat("/companyprofile"), file);
		}

		return new ModificationResult<Long>(id);
	}

	@Transactional
	public ModificationResult<String> uploadCompanyProfile(String username, String uploadPath, MultipartFile file) {
		fileProvider.validateFile(file, Set.of("png", "jpg", "jpeg")); // validating file

		var company = companyRepo.findByEmail(username).orElseThrow(
				() -> new BusinessException("Firstly,fill company information before uploading profile image "));

		try {
			var profileImageName = fileProvider.saveFile(uploadPath, company.getAccount().getEmail(), file);
			company.setProfilePhoto(profileImageName);

			return new ModificationResult<String>("Profile is successfully uploaded" + profileImageName);

		} catch (IOException e) {
			throw new FileInvalidException("Profile upload failed", e);
		}
	}
}
