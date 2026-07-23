package com.dev.anh.job.model.repo;

import java.util.List;
import java.util.Optional;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Job;

public interface JobRepo extends BaseRepository<Job, Long>{
	List<Job> findByCompanyId(Long companyId);
	Optional<Job> findFirstByOrderByCreateAt();
}