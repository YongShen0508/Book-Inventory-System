package com.stock.bookinventory.repository;

import com.stock.bookinventory.mapper.OrderItemMapper;
import org.springframework.stereotype.Repository;

@Repository
public class OrderItemRepository {

    private final OrderItemMapper orderItemMapper;

    public OrderItemRepository(OrderItemMapper orderItemMapper) {
        this.orderItemMapper = orderItemMapper;
    }

}
