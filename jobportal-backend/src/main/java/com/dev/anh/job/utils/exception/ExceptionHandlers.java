package com.dev.anh.job.utils.exception;

import java.nio.file.AccessDeniedException;
import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

@RestControllerAdvice
public class ExceptionHandlers {

	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	List<String> handle(BusinessException e) {
		 return List.of(e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	List<String>handle(FileInvalidException e) {
		 return List.of(e.getMessage());
	}
	
	
	//Handle Invalid Enum Type
	@ExceptionHandler
	@ResponseStatus(code= HttpStatus.BAD_REQUEST)
	List<String> handle(HttpMessageNotReadableException e) {

	    Throwable cause = e.getCause();

	    if (cause instanceof InvalidFormatException ife
	            && ife.getTargetType().isEnum()) {

	        String message = "Invalid value '" + ife.getValue()
	                + "'. Allowed values are: "
	                + Arrays.toString(ife.getTargetType().getEnumConstants());

	        return List.of(message);
	    }

	    return List.of("Invalid request body");
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	List<String> handle(MethodArgumentNotValidException e) {
	  return e.getFieldErrors().stream()
			  .map(a -> a.getDefaultMessage())
			  .toList();
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.GONE)
	List<String> handle(TokenExpirationException e) {
		return List.of(e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
	List<String> handle(AuthenticationException e) {
		 return List.of(e.getMessage());
	}
	
	@ExceptionHandler
	@ResponseStatus(code = HttpStatus.FORBIDDEN)
	List<String> handle(AccessDeniedException e) {
		 return List.of(e.getMessage());
	}	
}
