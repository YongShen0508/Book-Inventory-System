package com.stock.bookinventory.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import com.stock.bookinventory.model.OrderItemStatus;

import lombok.Data;

@Data
public class OrderItemResponseDTO {
	private Long id;
	private Long orderId;
	private Long bookId;
	private String bookTitle;
	private Integer quantity;
	private BigDecimal subtotal;
	private OrderItemStatus status;
	private Date createdAt;
	private Date updatedAt;
}
