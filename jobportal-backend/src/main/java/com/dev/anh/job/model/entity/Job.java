package com.dev.anh.job.model.entity;

import java.util.List;

import com.dev.anh.job.model.consts.JobLevel;
import com.dev.anh.job.model.consts.JobType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Job extends AbstractEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="job_id")
	private Long id;
	
	@Column(nullable = false)
	private String positionName;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String JobDescription;
	
	@Column(nullable = false)
	@Positive(message = "Salary must be greater than 0")
	private Double salary;
	
	@Column(nullable =  false)
	@Enumerated(EnumType.STRING)
	private JobLevel jobLevel;
	
	@Column(nullable =  false)
	@Enumerated(EnumType.STRING)
	private JobType jobType;
	
	@ManyToOne(optional = false)	
	private Company company;
	
	@OneToMany(mappedBy = "job", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<JobApply> jobApply;
	
	private boolean deleted;
	
}
