package com.stock.bookinventory.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequestDTO {

	@NotNull(message = "Book ID must not be null")
	@Min(value = 1, message = "Book ID must be at least 1")
	private Long bookId;

	@NotNull(message = "Quantity must not be null")
	@Min(value = 1, message = "Quantity must be at least 1")
	@Max(value = 10000, message = "Quantity must not exceed 10000")
	private Integer quantity;
}
