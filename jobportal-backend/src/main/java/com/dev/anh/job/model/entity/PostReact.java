package com.dev.anh.job.model.entity;

import com.dev.anh.job.model.entity.embeddable.PostReactPk;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "React_Post")
@EqualsAndHashCode(callSuper = false)
public class PostReact extends AbstractEntity{

	@EmbeddedId
	private PostReactPk id;
	
	@ManyToOne
	@JoinColumn(name="account_id", insertable = false, updatable = false)
	private Account account;
	
	@ManyToOne
	@JoinColumn(name = "post_id", insertable = false, updatable = false)
	private Post post;
	
	private Boolean reactPost;
}
