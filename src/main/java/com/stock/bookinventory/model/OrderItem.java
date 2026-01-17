package com.stock.bookinventory.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {

    private Long id;
    private Long orderId;
    private Long bookId;
    private Integer quantity;
    private BigDecimal subtotal;
    private OrderItemStatus status;
    private Date createdAt;
    private Date updatedAt;
}
