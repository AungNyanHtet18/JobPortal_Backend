package com.dev.anh.job.event;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.dev.anh.job.utils.service.JobApplyEmailService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class JobApplicationEventListener {

	private final JobApplyEmailService emailService;
	
	@Async
	@EventListener
	public void handleJobApplicationEvent(JobApplicationEvent event) {
		log.info("Job Application Event: email={}, name={}, status={}, note={}, company={}, jobTitle={}, appliedAt={}",
		        event.getApplicantEmail(),
		        event.getApplicantName(),
		        event.getStatus(),
		        event.getNote(),
		        event.getCompanyName(),
		        event.getJobTitle(),
		        event.getAppliedAt()
		);
		
		emailService.sendEmail(event.getApplicantEmail(), 
				               event.getApplicantName(), 
				               event.getStatus(), 
				               event.getNote(),
				               event.getCompanyName(), 
				               event.getJobTitle(), 
				               event.getAppliedAt());
	}
	
	
}
