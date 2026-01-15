package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.consts.Status;
import com.dev.anh.job.model.entity.embeddable.JobApplyPk;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "JobApply")
@EqualsAndHashCode(callSuper = false)
public class JobApply extends AbstractEntity{

	@EmbeddedId
	private JobApplyPk id;
	
	@ManyToOne
	@JoinColumn(name="applicant_id", insertable = false, updatable = false)
	private Applicant applicant;
	
	@ManyToOne
	@JoinColumn(name = "job_id", insertable = false, updatable = false)
	private Job job;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;
	
}
