package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.consts.Gender;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Applicant extends AbstractEntity{

	@Id
	@Column(name = "applicant_id")
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "applicant_id")
	private Account account;

	@Column(nullable = false)
	private Gender gender;
			
	@Column(nullable = false)
	private String skills;
		
	private String highestEducationalAttainment;
	
	private String professionalSummary;
	
	private String resumeUrl;
	private String coverLetter;
	
	private String profilePhoto;
	
	private String currentJob;
	
	@Column(nullable = false)
	private String contactDetail;
	
	@Column(nullable = false)
	private String address;
	
	
}
