package com.stock.bookinventory.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDTO {

	@NotNull(message = "Order ID is required for update")
	@Min(value = 1, message = "Order ID must be at least 1")
	private Long id;

	@NotNull(message = "Customer ID must not be null")
	@Min(value = 1, message = "Customer ID must be at least 1")
	private Long customerId;

	@NotNull(message = "Order items must not be null")
	@NotEmpty(message = "Order must contain at least one item")
	@Valid
	private List<OrderItemRequestDTO> items;
}
