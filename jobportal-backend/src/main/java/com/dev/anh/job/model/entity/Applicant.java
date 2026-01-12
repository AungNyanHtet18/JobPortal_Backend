package com.dev.anh.job.model.entity;

import java.util.List;

import com.dev.anh.job.model.consts.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Size;
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
	@Enumerated(EnumType.STRING)
	private Gender gender;
			
	@Column(nullable = false)
	private String skills;
		
	@Column(columnDefinition = "TEXT")
	private String professionalSummary;
	
	private String resume;	
	private String profilePhoto;
	
	private String highestEducationalAttainment;
	
	@Column(nullable = false)
	@Size(min = 10, max = 200, message = "Contact details must be between 10 and 200")
	private String contactDetail;
	
	@Column(nullable = false)
	@Size(min = 10, max = 200, message = "Address must be between 10 and 200")
	private String address;
	
	private boolean deleted = false;
	
	@OneToMany(mappedBy = "applicant")
	private List<JobApply> jobApply;
	
}
