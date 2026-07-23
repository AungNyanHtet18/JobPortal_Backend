package com.dev.anh.job.model.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class PostReactPk {

	@Column(name = "account_id")
	private Long accountId;
	@Column(name = "post_id")
	private Long postId;
	
	public PostReactPk(Long accountId, Long postId) {
	   this.accountId = accountId;
	   this.postId = postId;
	}
	
}
