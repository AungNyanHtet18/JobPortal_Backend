package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.entity.embeddable.SavedJobPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "SavedJob")
@EqualsAndHashCode(callSuper = false)
public class SavedJob extends AbstractEntity{

	@EmbeddedId
	private SavedJobPk id;
	
	@ManyToOne
	@JoinColumn(name="applicant_id", insertable = false, updatable = false)
	private Applicant applicant;
	
	@ManyToOne
	@JoinColumn(name = "job_id", insertable = false, updatable = false)
	private Job job;
	
	private Boolean savedJob;
	
}
