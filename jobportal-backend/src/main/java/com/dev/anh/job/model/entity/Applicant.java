package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.consts.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Applicant {

	@Id
	@Column(name = "applicant_id")
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "applicant_id")
	private Account account;
	
	@Column(nullable = false)
	private String applicantName;
	
	private String contactDetails;
	
	@Column(nullable = false)
	private Gender gender;
	
	private String professionalSummary;
	
	private String highestEducationalAttainment;
	
	private String resumeUrl;
	
	private String skills;
	
}
