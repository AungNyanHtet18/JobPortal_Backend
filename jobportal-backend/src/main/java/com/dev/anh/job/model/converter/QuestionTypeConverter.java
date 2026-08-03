package com.dev.anh.job.model.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.dev.anh.job.model.consts.QuestionType;

@Component
public class QuestionTypeConverter implements Converter<String, QuestionType>{

	@Override
	public QuestionType convert(String source) {
		return QuestionType.fromString(source);
	}	
}
