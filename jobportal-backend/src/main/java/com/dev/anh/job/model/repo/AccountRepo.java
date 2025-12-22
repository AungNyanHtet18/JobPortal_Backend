package com.dev.anh.job.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.anh.job.model.entity.Account;

public interface AccountRepo extends JpaRepository<Account, Long>{

	Optional<Account>findOneByEmail(String email);
}
