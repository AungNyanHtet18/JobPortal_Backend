package com.dev.anh.job;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.dev.anh.job.model.BaseRepositoryImpl;

@Configuration
@EnableJpaRepositories(repositoryBaseClass =  BaseRepositoryImpl.class)
@EnableJpaAuditing
public class JobprotalJpaConfiguration {
}
