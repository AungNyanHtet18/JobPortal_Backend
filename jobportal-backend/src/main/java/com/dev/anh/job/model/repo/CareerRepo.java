package com.dev.anh.job.model.repo;

import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Career;

public interface CareerRepo extends BaseRepository<Career, Long>{
		Optional<Career> findOneByRoleName(String roleName);
}
