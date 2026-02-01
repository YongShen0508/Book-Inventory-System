package com.stock.bookinventory.exception;

public class GeneralException extends RuntimeException {
	public String code;

	public GeneralException(String code, String message) {
		super(message);
		this.code = code;
	}
	public GeneralException(String code, String message, Throwable cause) {

		super(message, cause);
		this.code = code;

	}

	public String getCode() {
		return this.code;
	}

}
