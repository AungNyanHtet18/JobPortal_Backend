package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.consts.LanguageLevel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Language {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String languageName;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private LanguageLevel languageLevel;
}