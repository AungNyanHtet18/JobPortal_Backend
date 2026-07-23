package com.dev.anh.job.admin.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.anh.job.admin.model.input.YearMonthData;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;
import com.dev.anh.job.model.repo.JobRepo;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobDashboardService {

	private final JobRepo jobRepo;
	
	public List<Integer> getYear() {
		var startYear = jobRepo.findFirstByOrderByCreateAt()
				.map(a -> a.getCreateAt().getYear())
				.orElse(LocalDateTime.now().getYear());
		
		var currentYear = LocalDate.now().getYear();
		
		if(startYear == currentYear) {
		   return List.of(startYear);
		}
		
		return IntStream.rangeClosed(startYear, currentYear)
				 .boxed()
				 .toList();
	}

	public Map<LocalDate, Long> getJobPostings(YearMonthData data) {
		
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
		 return jobRepo.searchOne(cb -> {
			var cq = cb.createQuery(Long.class);
			var root = cq.from(Job.class);
			
			cq.select(
				cb.count(root.get(Job_.id))	
			);
			
			cq.where(
			   cb.greaterThanOrEqualTo(root.get(Job_.createAt), start),
			   cb.lessThan(root.get(Job_.createAt) , next)
			);
			
			 return cq;
		 }).orElse(0L);
	}
	 
}

