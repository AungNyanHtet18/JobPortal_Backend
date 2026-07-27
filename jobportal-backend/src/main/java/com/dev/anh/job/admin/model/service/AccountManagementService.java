package com.dev.anh.job.admin.model.service;

import java.util.function.Function;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.admin.model.input.ApplicantSearch;
import com.dev.anh.job.admin.model.input.CompanySearch;
import com.dev.anh.job.admin.model.output.ApplicantListItem;
import com.dev.anh.job.admin.model.output.CompanyListItem;
import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.Applicant_;
import com.dev.anh.job.model.entity.Company;
import com.dev.anh.job.model.entity.Company_;
import com.dev.anh.job.model.output.PageResult;
import com.dev.anh.job.model.repo.ApplicantRepo;
import com.dev.anh.job.model.repo.CompanyRepo;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountManagementService {

	private final ApplicantRepo applicantRepo;
	private final CompanyRepo companyRepo;
	
	public PageResult<ApplicantListItem> searchApplicant(ApplicantSearch applicantSearch, int page, int size) {
		return applicantRepo.search(queryFunc(applicantSearch), countFunc(applicantSearch), page, size);
	}
	
	public PageResult<CompanyListItem> searchCompany(CompanySearch companySearch, int page, int size) {
		return companyRepo.search(queryFunc(companySearch), countFunc(companySearch), page, size);
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<ApplicantListItem>> queryFunc(ApplicantSearch applicantSearch) {
		return cb -> {
		    var cq = cb.createQuery(ApplicantListItem.class);
			var root = cq.from(Applicant.class);
			
			var account = root.join(Applicant_.account, JoinType.INNER);
			ApplicantListItem.select(cq, cb, root, account);
			cq.where(applicantSearch.where(cb, root, account));
			cq.orderBy(cb.desc(root.get(Applicant_.createdAt)));
		   
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(ApplicantSearch applicantSearch) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Applicant.class); 
			
			var account = root.join(Applicant_.account, JoinType.INNER);
			cq.select(cb.count(root.get(Applicant_.id)));
			cq.where(applicantSearch.where(cb, root, account));
			
			return cq; 
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<CompanyListItem>> queryFunc(CompanySearch companySearch) {
		return cb -> {
			var cq = cb.createQuery(CompanyListItem.class);
			var root = cq.from(Company.class);
			
			var account = root.join(Company_.account, JoinType.INNER);
			CompanyListItem.select(cq, cb, root, account);
			cq.where(companySearch.where(cb, root, account));
			cq.orderBy(cb.desc(root.get(Company_.createdAt)));
			
			return cq;
		};
	}
	
	private Function<CriteriaBuilder, CriteriaQuery<Long>> countFunc(CompanySearch companySearch) {
		return cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Company.class);
			
			var account = root.join(Company_.account, JoinType.INNER);
			cq.select(cb.count(root.get(Company_.id)));
			cq.where(companySearch.where(cb, root, account));
			
			return cq;
		};
	}
}
