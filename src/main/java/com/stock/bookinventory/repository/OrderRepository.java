package com.stock.bookinventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.stock.bookinventory.mapper.OrderMapper;
import com.stock.bookinventory.model.Order;

@Repository
public class OrderRepository {

	private final OrderMapper orderMapper;

	public OrderRepository(OrderMapper orderMapper) {
		this.orderMapper = orderMapper;
	}

	public int insert(Order order) {
		return orderMapper.insert(order);
	}

	public List<Order> findAll(int pageSize, int offset) {
		return orderMapper.findAll(pageSize, offset);
	}

	public Optional<Order> findById(Long id) {
		return Optional.ofNullable(orderMapper.findById(id));
	}

	public int update(Order order) {
		return orderMapper.update(order);
	}

	public int deleteById(Long id) {
		return orderMapper.deleteById(id);
	}

	public List<Order> findByCustomerId(Long customerId) {
		return orderMapper.findByCustomerId(customerId);
	}

	public List<Order> findByStatus(String status) {
		return orderMapper.findByStatus(status);
	}

	public List<Order> findExpiredOrder(int pageSize, int offset) {
		return orderMapper.findExpiredOrder(pageSize, offset);
	}

	public Order getOrderForUpdate(Long id) {
		return orderMapper.getOrderForUpdate(id);
	}
}
