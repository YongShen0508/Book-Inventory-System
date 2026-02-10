package com.stock.bookinventory.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.stock.bookinventory.mapper.OrderItemMapper;
import com.stock.bookinventory.model.OrderItem;

@Repository
public class OrderItemRepository {

	private final OrderItemMapper orderItemMapper;

	public OrderItemRepository(OrderItemMapper orderItemMapper) {
		this.orderItemMapper = orderItemMapper;
	}

	public int insert(OrderItem orderItem) {
		return orderItemMapper.insert(orderItem);
	}

	public int batchInsert(List<OrderItem> orderItems) {
		return orderItemMapper.batchInsert(orderItems);
	}

	public List<OrderItem> findAll(Integer pageSize, Integer offset) {
		return orderItemMapper.findAll(pageSize, offset);
	}

	public Optional<OrderItem> findById(Long id) {
		return Optional.ofNullable(orderItemMapper.findById(id));
	}

	public Optional<OrderItem> selectById(Long id) {
		return Optional.ofNullable(orderItemMapper.selectById(id));
	}

	public Optional<OrderItem> getOrderItemForUpdate(Long id) {
		return Optional.ofNullable(orderItemMapper.getOrderItemForUpdate(id));
	}

	public int update(OrderItem orderItem) {
		return orderItemMapper.update(orderItem);
	}

	public int updateStatus(Long orderItemId, String status) {
		return orderItemMapper.updateStatus(orderItemId, status);
	}

	public int updateQuantity(Long orderItemId, Integer quantity, BigDecimal subtotal) {
		return orderItemMapper.updateQuantity(orderItemId, quantity, subtotal);
	}

	public int deleteById(Long id) {
		return orderItemMapper.deleteById(id);
	}

	public int deleteByOrderId(Long orderId) {
		return orderItemMapper.deleteByOrderId(orderId);
	}

	public List<OrderItem> findByOrderId(Long orderId) {
		return orderItemMapper.findByOrderId(orderId);
	}

	public List<OrderItem> findByBookId(Long bookId) {
		return orderItemMapper.findByBookId(bookId);
	}

	public List<OrderItem> findByStatus(String status) {
		return orderItemMapper.findByStatus(status);
	}

	public int getTotalQuantityByOrderId(Long orderId) {
		return orderItemMapper.getTotalQuantityByOrderId(orderId);
	}

	public boolean existsByOrderIdAndBookId(Long orderId, Long bookId) {
		return orderItemMapper.existsByOrderIdAndBookId(orderId, bookId);
	}
}
