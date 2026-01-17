package com.stock.bookinventory.mapper;

import com.stock.bookinventory.model.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Mapper
public interface OrderMapper {

    // Insert
    int insert(Order order);

    // Find operations
    List<Order> findAll();

    Order findById(@Param("id") Long id);

    Order selectById(@Param("id") Long id);

    Order getOrderForUpdate(@Param("id") Long id);

    List<Order> findByCustomerId(@Param("customerId") Long customerId);

    List<Order> findByStatus(@Param("status") String status);

    List<Order> findByCriteria(@Param("customerId") Long customerId,
                                @Param("status") String status,
                                @Param("minTotalAmount") BigDecimal minTotalAmount,
                                @Param("maxTotalAmount") BigDecimal maxTotalAmount,
                                @Param("startDate") Date startDate,
                                @Param("endDate") Date endDate);

    List<Order> findExpiredOrders();

    List<Order> findPendingOrders();

    // Update operations
    int update(Order order);

    int updateStatus(@Param("orderId") Long orderId, @Param("status") String status);

    int updateTotalAmount(@Param("orderId") Long orderId, @Param("totalAmount") BigDecimal totalAmount);

    // Delete operations
    int deleteById(@Param("id") Long id);

    // Utility operations
    Long countTotal();

    Long countByStatus(@Param("status") String status);

    Long countByCustomerId(@Param("customerId") Long customerId);

    BigDecimal calculateTotalRevenue();

    BigDecimal calculateRevenueByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
