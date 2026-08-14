package com.dev.anh.job.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dev.anh.job.model.BaseRepository;
import com.dev.anh.job.model.entity.Company;

public interface CompanyRepo extends BaseRepository<Company, Long>{

	@Query("select c from Company c where c.account.email = :email")
	Optional<Company>findOneByCompanyName(@Param("email") String email);

	@Query("select c from Company c where c.account.email = :email")
	Optional<Company> findByEmail(@Param("email") String email);
	
	@Query("SELECT c.id FROM Company c JOIN c.account a WHERE a.email =:email")
	Optional<Long> findIdByAccountEmail(String email);
}
