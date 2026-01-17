package com.stock.bookinventory.dto.response;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class OrderResponseDTO {
	private Long id;
	private Long customerId;
	private String customerName;
	private Date orderDate;
	private String status;
	private BigDecimal totalAmount;
	private Date orderAt;
	private Date expiresAt;
	private Date createdAt;
	private Date updatedAt;
	private List<OrderItemResponseDTO> orderItems;
}
