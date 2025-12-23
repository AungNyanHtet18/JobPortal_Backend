package com.dev.anh.job.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.anh.job.model.entity.Company;

public interface CompanyRepo extends JpaRepository<Company, Long>{

}
