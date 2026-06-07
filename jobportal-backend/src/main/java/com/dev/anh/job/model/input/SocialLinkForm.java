package com.dev.anh.job.model.input;

import com.dev.anh.job.model.entity.Applicant;
import com.dev.anh.job.model.entity.SocialLink;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record SocialLinkForm(
	@NotNull(message = "Please fill your platform")
	String platform,
	@NotBlank(message = "Please fill your social link ur url")
	String url) {
	
	public static SocialLink ApplicantSocialLink(Applicant applicant, SocialLinkForm form) {
		 var social = new SocialLink();
		 social.setApplicant(applicant);
		 social.setPlatform(form.platform());
		 social.setUrl(form.url());
		 return social;
	}
}
