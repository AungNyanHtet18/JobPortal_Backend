package com.dev.anh.job.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Company {

	@Id	
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "id")
	private Account account;
	
	@Column(nullable = false)
	private String companyName;
	
	@Column(nullable = false)
	private String location;
	
	private String phone;
}
