package com.stock.bookinventory.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

	private Long id;
	private Long orderId;
	private Long bookId;
	private Integer quantity;
	private BigDecimal price;
	private BigDecimal subtotal;
	private OrderItemStatus status;
	private Date createdAt;
	private Date updatedAt;
}
