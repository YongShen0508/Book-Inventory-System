package com.stock.bookinventory.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private Long id;
    private Long customerId;
    private Date orderDate;
    private String status;
    private BigDecimal totalAmount;
    private Date orderAt;
    private Date expiresAt;
    private Date createdAt;
    private Date updatedAt;
}
