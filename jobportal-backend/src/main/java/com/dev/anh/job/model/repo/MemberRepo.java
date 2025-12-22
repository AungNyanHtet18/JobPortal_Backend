package com.dev.anh.job.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dev.anh.job.model.entity.Member;

public interface MemberRepo extends JpaRepository<Member, Long> {

}
