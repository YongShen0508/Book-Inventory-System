package com.stock.bookinventory.repository;

import com.stock.bookinventory.mapper.OrderMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private final OrderMapper orderMapper;

    public OrderRepository(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }
}
