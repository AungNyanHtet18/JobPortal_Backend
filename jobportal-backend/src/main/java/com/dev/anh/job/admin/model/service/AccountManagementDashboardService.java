package com.dev.anh.job.admin.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dev.anh.job.admin.model.input.YearMonthData;
import com.dev.anh.job.model.entity.Account;
import com.dev.anh.job.model.entity.Account_;
import com.dev.anh.job.model.repo.AccountRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountManagementDashboardService {

	private final AccountRepo accountRepo;
	
	public Map<LocalDate, Long> getMemberSummary(YearMonthData data) {
		var result = new LinkedHashMap<LocalDate, Long>();
		
		var start = data.getStartDate();
		var end = data.getEndDate();
		
		while(start.isBefore(end)) {
		    var next = data.next(start);
		    result.put(start.toLocalDate(), getCount(start, next));
		    start = next;
		}
		
		return result;
	}
	
	private Long getCount(LocalDateTime start, LocalDateTime next) {
		 return accountRepo.searchOne(cb -> {
			var cq = cb.createQuery(Long.class); 
			var root = cq.from(Account.class);
			
			cq.select(
			  cb.count(root.get(Account_.id))
			);
	
			cq.where(
			  cb.greaterThanOrEqualTo(root.get(Account_.createdAt), start),
			  cb.lessThan(root.get(Account_.createdAt), next)
			);
			
			 return cq;
		 }).orElse(0L);
	}
}
