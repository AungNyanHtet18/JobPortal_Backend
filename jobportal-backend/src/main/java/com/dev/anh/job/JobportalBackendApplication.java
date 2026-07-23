package com.dev.anh.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class JobportalBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobportalBackendApplication.class, args);
	}
}
