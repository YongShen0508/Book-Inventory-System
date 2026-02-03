package com.stock.bookinventory.mapper;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stock.bookinventory.model.Order;

@Mapper
public interface OrderMapper {

	// Insert
	int insert(Order order);

	// Find operations
	List<Order> findAll();

	<Optional> Order findById(@Param("id") Long id);

	Order selectById(@Param("id") Long id);

	Order getOrderForUpdate(@Param("id") Long id);

	List<Order> findByCustomerId(@Param("customerId") Long customerId);

	List<Order> findByStatus(@Param("status") String status);

	List<Order> findExpiredOrders();

	List<Order> findPendingOrders();

	// Update operations
	int update(Order order);

	int updateStatus(@Param("orderId") Long orderId, @Param("status") String status);

	int updateTotalAmount(@Param("orderId") Long orderId, @Param("totalAmount") BigDecimal totalAmount);

	// Delete operations
	int deleteById(@Param("id") Long id);

	// Utility operations

	BigDecimal calculateRevenueByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
