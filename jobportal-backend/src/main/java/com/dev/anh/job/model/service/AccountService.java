package com.dev.anh.job.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.utils.exception.BusinessException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {
	
	private final AccountRepo accountRepo;
	
	public ModificationResult<Boolean> checkRoleStatus(String email) {
		var account = accountRepo.findOneByEmail(email).orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(email)));
		return new ModificationResult<Boolean>(account.getRoleStatus());
	}
	
	public Account findAccount(String username) {
		return accountRepo.findOneByEmail(username)
				.orElseThrow(() -> new BusinessException("Account with %s is not found".formatted(username)));
	}
	
	public Account findAccount(Long accountId) {
		return accountRepo.findById(accountId)
				.orElseThrow(() -> new BusinessException("Account with ID: %d is not found".formatted(accountId)));
	}

}
