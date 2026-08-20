package com.dev.anh.job.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dev.anh.job.model.consts.JobLevel;

@Component
public class JobLevelConverter implements Converter<String, JobLevel>{

	@Override
	public JobLevel convert(String source) {

		if(source == null || source.isBlank()) {
			 return null;
		}
		
		return JobLevel.fromString(source);
	}
}
