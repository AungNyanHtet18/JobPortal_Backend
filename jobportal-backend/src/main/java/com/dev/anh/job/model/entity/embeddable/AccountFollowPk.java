package com.dev.anh.job.model.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Embeddable
@NoArgsConstructor
public class AccountFollowPk {

	@Column(name = "follower_id")
	private Long followerId;
	
	@Column(name = "following_id")
	private Long followingId;
	
	public AccountFollowPk(Long followerId,Long followingId) {
		 this.followerId = followerId;
		 this.followingId = followingId;
	}
	
}
