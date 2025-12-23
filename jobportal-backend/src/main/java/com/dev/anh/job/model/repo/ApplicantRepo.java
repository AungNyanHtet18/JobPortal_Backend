package com.dev.anh.job.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.anh.job.model.entity.Applicant;

public interface ApplicantRepo extends JpaRepository<Applicant, Long>{

}
