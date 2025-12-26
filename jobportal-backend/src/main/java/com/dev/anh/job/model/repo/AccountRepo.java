package com.dev.anh.job.model.repo;

import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Account;

public interface AccountRepo extends BaseRepository<Account, Long>{

	Optional<Account>findOneByEmail(String email);
}
