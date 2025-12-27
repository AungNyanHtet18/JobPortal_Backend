package com.dev.anh.job.model.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Company {

	@Id	
	@Column(name = "company_id")
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "company_id")
	private Account account;
	
	@Column(nullable = false)
	private String companyName;
	
	@Column(nullable = false)
	private String location;
	
	private String phone;
	
	private String website;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	private LocalDateTime createAt;
	private LocalDateTime updateAt;
	
	@OneToMany(mappedBy = "company")
	private List<Job> jobs;
	
	
}
