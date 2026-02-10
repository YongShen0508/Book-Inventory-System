package com.stock.bookinventory.model;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

	private Long id;
	private Long customerId;
	private Date orderDate;
	private OrderStatus status;
	private BigDecimal totalAmount;
	private Date orderAt;
	private Date expiresAt;
	private Date createdAt;
	private Date updatedAt;
}
