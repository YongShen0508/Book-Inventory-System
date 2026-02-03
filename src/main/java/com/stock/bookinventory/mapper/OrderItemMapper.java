package com.stock.bookinventory.mapper;

import java.math.BigDecimal;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stock.bookinventory.model.OrderItem;

@Mapper
public interface OrderItemMapper {

	// Insert
	int insert(OrderItem orderItem);

	int batchInsert(@Param("list") List<OrderItem> orderItems);

	// Find operations
	List<OrderItem> findAll(Integer pageSize, Integer offset);

	OrderItem findById(@Param("id") Long id);

	OrderItem selectById(@Param("id") Long id);

	OrderItem getOrderItemForUpdate(@Param("id") Long id);

	List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

	List<OrderItem> findByBookId(@Param("bookId") Long bookId);

	List<OrderItem> findByStatus(@Param("status") String status);

	// Update operations
	int update(OrderItem orderItem);

	int updateStatus(@Param("orderItemId") Long orderItemId, @Param("status") String status);

	int updateQuantity(@Param("orderItemId") Long orderItemId, @Param("quantity") Integer quantity,
			@Param("subtotal") BigDecimal subtotal);

	// Delete operations
	int deleteById(@Param("id") Long id);

	int deleteByOrderId(@Param("orderId") Long orderId);

	// Utility operations

	BigDecimal calculateTotalByOrderId(@Param("orderId") Long orderId);

	Integer getTotalQuantityByOrderId(@Param("orderId") Long orderId);

	boolean existsByOrderIdAndBookId(@Param("orderId") Long orderId, @Param("bookId") Long bookId);
}
