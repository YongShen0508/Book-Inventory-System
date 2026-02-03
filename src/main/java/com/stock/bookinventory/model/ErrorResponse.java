package com.stock.bookinventory.model;

import java.time.LocalDateTime;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ErrorResponse {

	private int status;
	private String error;
	private String errorCode;
	private String message;
	private String path;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime timestamp;

	private Map<String, String> validationErrors;

	public ErrorResponse() {
		this.timestamp = LocalDateTime.now();
	}

	public ErrorResponse(int status, String error, String errorCode, String message, String path) {
		this.status = status;
		this.error = error;
		this.errorCode = errorCode;
		this.message = message;
		this.path = path;
		this.timestamp = LocalDateTime.now();
	}

	public ErrorResponse(int status, String error, String errorCode, String message, String path,
			Map<String, String> validationErrors) {
		this.status = status;
		this.error = error;
		this.errorCode = errorCode;
		this.message = message;
		this.path = path;
		this.timestamp = LocalDateTime.now();
		this.validationErrors = validationErrors;
	}
}
