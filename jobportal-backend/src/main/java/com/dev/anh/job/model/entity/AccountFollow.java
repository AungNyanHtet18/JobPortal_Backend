package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.entity.embeddable.AccountFollowPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "Account_Follow")
@EqualsAndHashCode(callSuper = false)
public class AccountFollow extends AbstractEntity{

	@EmbeddedId
	private AccountFollowPk id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="follower_id", insertable = false, updatable = false)
	private Account follower;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="following_id", insertable = false, updatable = false)
	private Account following; 
	
}
