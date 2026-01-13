package com.dev.anh.job.model.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class JobApplyPk {

	@Column(name = "job_applicant_id")
	private Long applicantId;
	@Column(name = "job_apply_id")
	private Long jobId;
	
	public JobApplyPk(Long applicantId,Long jobId) {
		 this.applicantId = applicantId;
		 this.jobId = jobId;
	}
	
}
