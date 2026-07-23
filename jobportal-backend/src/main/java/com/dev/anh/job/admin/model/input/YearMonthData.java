package com.dev.anh.job.admin.model.input;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record YearMonthData(
	Type type,
	Integer year,
	Integer month) {
	
	public enum Type {
	   Yearly, Monthly
	}
	
	public LocalDateTime getStartDate() {
		 return type == Type.Yearly ? LocalDate.of(year, 1, 1).atStartOfDay() : LocalDate.of(year, month, 1).atStartOfDay();
	}
	
	public LocalDateTime getEndDate() {
		var startDate = getStartDate();
		return type == Type.Yearly ? startDate.plusYears(1) : startDate.plusMonths(1);
	}
	
	public LocalDateTime next(LocalDateTime date) {
		 return type == Type.Yearly ? date.plusMonths(1) : date.plusDays(1);
	}

}
