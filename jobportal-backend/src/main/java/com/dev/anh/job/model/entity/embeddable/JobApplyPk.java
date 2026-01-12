package com.dev.anh.job.model.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Embeddable
@AllArgsConstructor
public class JobApplyPk {

	@Column(name = "job_applicant_id")
	private Long applicantId;
	@Column(name = "job_apply_id")
	private Long jobId;
	
}
