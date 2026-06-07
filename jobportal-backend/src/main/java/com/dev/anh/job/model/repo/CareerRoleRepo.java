package com.dev.anh.job.model.repo;

import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.CareerRole;

public interface CareerRoleRepo extends BaseRepository<CareerRole, Long>{
		Optional<CareerRole> findOneByRoleName(String roleName);
}
