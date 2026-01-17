package com.stock.bookinventory.dto.request;

import com.stock.bookinventory.model.OrderStatus;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderStatusRequestDTO {

	@NotNull(message = "Order ID must not be null")
	@Min(value = 1, message = "Order ID must be at least 1")
	private Long id;

	@NotNull(message = "Order status must not be null")
	private OrderStatus status;
}
