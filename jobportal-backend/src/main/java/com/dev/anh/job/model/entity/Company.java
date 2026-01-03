package com.dev.anh.job.model.entity;

import java.util.List;

import org.hibernate.validator.constraints.URL;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class Company extends AbstractEntity{

	@Id	
	@Column(name = "company_id")
	private Long id;
	
	@OneToOne
	@MapsId
	@JoinColumn(name = "company_id")
	private Account account;
	
	@Column(nullable = false)
	@Size(min = 5, max = 200, message = "Address must be between 10 and 200")
	private String location;
	
	@NotNull(message = "Phone number must not be null")
	@Pattern(regexp = "^\\d{10}$", message =  "Invalid phone number format,expected 10 digit")
	@Column(nullable = false)
	private String phone;
	
	@URL(message = "Invalid URL Format")
	private String websiteUrl;
	
	@Column(nullable = false, columnDefinition = "TEXT")
	private String description;
	
	@OneToMany(mappedBy = "company")
	private List<Job> jobs;

}
