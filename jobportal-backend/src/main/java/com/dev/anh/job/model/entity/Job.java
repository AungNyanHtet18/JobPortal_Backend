package com.dev.anh.job.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Job {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private String positionName;
	
	@Column(nullable = false)
	private String summaryForPosition;
	
	@Column(nullable = false)
	private Double salary;
	
	@ManyToOne
	@JoinColumn(name = "company_id", nullable = false)
	private Company company;
}
