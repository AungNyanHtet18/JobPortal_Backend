package com.dev.anh.job.model.output;

import com.dev.anh.job.model.entity.SocialLink;

public record ApplicantSocialLinkDetails(
	String platform,
	String url) {

	public static ApplicantSocialLinkDetails from(SocialLink socialLink) {
		 return new ApplicantSocialLinkDetails(
				 socialLink.getPlatform(), 
				 socialLink.getUrl());
	}
}
