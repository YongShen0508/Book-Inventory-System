package com.stock.bookinventory.dto.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
	private boolean success;
	private String message;
	private T date;
	private Date timestamp;

	public static <T> ApiResponse<T> success(String message, T data) {
		return new ApiResponse<>(true, message, data, new Date());
	}

	private static <T> ApiResponse<T> success(String message) {
		return new ApiResponse<>(false, message, null, new Date());
	}

	private static <T> ApiResponse<T> error(String message) {
		return new ApiResponse<>(false, message, null, new Date());
	}

	private static <T> ApiResponse<T> error(String message, T data) {
		return new ApiResponse<>(false, message, data, new Date());
	}
}
