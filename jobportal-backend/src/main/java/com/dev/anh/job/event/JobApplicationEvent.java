package com.dev.anh.job.event;

import java.time.LocalDateTime;

import com.dev.anh.job.model.consts.ApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobApplicationEvent {

	private String applicantEmail;
    private String applicantName;
    private ApplicationStatus status;
    private String companyName;
    private String jobTitle;
    private LocalDateTime appliedAt;
	
}
