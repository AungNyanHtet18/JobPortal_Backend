package com.dev.anh.job.model.entity;

import java.time.LocalDate;

import com.dev.anh.job.model.consts.QualificationType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Education {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
		
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QualificationType qualificationType;
	
	@Column(nullable = false)
	private String qualificationName;
	
	@Column(nullable = false)
	private LocalDate completionDate;
	
	@ManyToOne(optional = false)
	private Applicant applicant;
	
}
