package com.dev.anh.job.model.entity;

import java.util.List;

import com.dev.anh.job.model.consts.QuestionType;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = false)
public class QuizQuestion extends AbstractEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(optional = false)
	@JoinColumn(name = "quiz_id")
	private Quiz quiz;
	
	@Column(nullable = false)
	private String questionTitle;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private QuestionType questionType;
	
	@Column(nullable = false)
	private Integer marks;
	
	@OneToMany(mappedBy = "quizQuestion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<QuestionOption> questionOptions;
}
