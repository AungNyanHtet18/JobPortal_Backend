package com.dev.anh.job.utils.exception;

public class EmailInvalidException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EmailInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public EmailInvalidException(String message) {
		super(message);
	}

}
