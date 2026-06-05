package com.dev.anh.job.model.entity;

import java.time.LocalDateTime;

import com.dev.anh.job.model.consts.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, unique = true)
	@Email(message = "Email should be valid")
	private String email;
	
	@Column(nullable = false)
	private String password;
	
	private Boolean active;
	private Boolean roleStatus;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private  Role role;
	
	private LocalDateTime activatedAt;
}
