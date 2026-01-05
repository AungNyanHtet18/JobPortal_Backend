package com.dev.anh.job.utils.exception;

public class FileInvalidException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public FileInvalidException(String message, Throwable cause) {
		super(message, cause);
	}

	public FileInvalidException(String message) {
		super(message);
	}

}
