package com.dev.anh.job.utils.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.mail")
public class EmailProperties {

	private String from;
	private String name;
}
