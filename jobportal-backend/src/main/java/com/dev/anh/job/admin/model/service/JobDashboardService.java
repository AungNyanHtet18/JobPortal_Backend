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
import com.dev.anh.job.admin.model.output.DashboardStats;
import com.dev.anh.job.model.entity.Job;
import com.dev.anh.job.model.entity.Job_;
import com.dev.anh.job.model.output.ModificationResult;
import com.dev.anh.job.model.repo.AccountRepo;
import com.dev.anh.job.model.repo.JobApplyRepo;
import com.dev.anh.job.model.repo.JobRepo;
import com.dev.anh.job.model.repo.PostRepo;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JobDashboardService {

	private final AccountRepo accountRepo;
	private final JobRepo jobRepo;
	private final JobApplyRepo jobApplyRepo;
	private final PostRepo postRepo;
	
	public ModificationResult<DashboardStats> getDashboardStats() {
		var totalUsers = accountRepo.count();
		var totalJobs = jobRepo.count();
		var totalApplications = jobApplyRepo.count();
		var totalPosts = postRepo.count();
		return new ModificationResult<DashboardStats>(new DashboardStats(totalUsers, totalJobs, totalApplications, totalPosts));
	}
	
	public List<Integer> getYear() {
		var startYear = jobRepo.findFirstByOrderByCreatedAt()
				.map(a -> a.getCreatedAt().getYear())
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
			   cb.greaterThanOrEqualTo(root.get(Job_.createdAt), start),
			   cb.lessThan(root.get(Job_.createdAt) , next)
			);
			
			 return cq;
			 
		 }).orElse(0L);
	}	 
}

