package com.dev.anh.job.model.entity;

import java.time.LocalDate;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Data
@Entity
public class Member {

	@Id
	private Long  id;
	
	@OneToOne(optional = false)
	@MapsId
	@JoinColumn(name = "id")
	private Account account;
	
	@Column(nullable = false)
	private String username;
	
	private String phone;
	private LocalDate birth;
	private String address;
}
