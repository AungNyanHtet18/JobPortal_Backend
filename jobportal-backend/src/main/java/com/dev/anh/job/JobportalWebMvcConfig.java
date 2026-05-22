package com.dev.anh.job;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JobportalWebMvcConfig implements WebMvcConfigurer{
	
	@Value("${app.upload.path}")
	private String uploadPath;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/profile/**")
				.addResourceLocations("file:"+ uploadPath.concat("/profile") + "/");
		
		registry.addResourceHandler("/companyprofile/**")
		.addResourceLocations("file:"+ uploadPath.concat("/companyprofile") + "/");
		
	}
	
}
