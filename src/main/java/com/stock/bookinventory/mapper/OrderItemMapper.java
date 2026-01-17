package com.stock.bookinventory.mapper;

import com.stock.bookinventory.model.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface OrderItemMapper {

    // Insert
    int insert(OrderItem orderItem);

    int batchInsert(@Param("list") List<OrderItem> orderItems);

    // Find operations
    List<OrderItem> findAll();

    OrderItem findById(@Param("id") Long id);

    OrderItem selectById(@Param("id") Long id);

    OrderItem getOrderItemForUpdate(@Param("id") Long id);

    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    List<OrderItem> findByBookId(@Param("bookId") Long bookId);

    List<OrderItem> findByStatus(@Param("status") String status);

    List<OrderItem> findByCriteria(@Param("orderId") Long orderId,
                                    @Param("bookId") Long bookId,
                                    @Param("status") String status,
                                    @Param("minQuantity") Integer minQuantity,
                                    @Param("maxQuantity") Integer maxQuantity,
                                    @Param("minSubtotal") BigDecimal minSubtotal,
                                    @Param("maxSubtotal") BigDecimal maxSubtotal);

    OrderItem findByOrderIdAndBookId(@Param("orderId") Long orderId, @Param("bookId") Long bookId);

    // Update operations
    int update(OrderItem orderItem);

    int updateStatus(@Param("orderItemId") Long orderItemId, @Param("status") String status);

    int updateQuantity(@Param("orderItemId") Long orderItemId,
                       @Param("quantity") Integer quantity,
                       @Param("subtotal") BigDecimal subtotal);

    // Delete operations
    int deleteById(@Param("id") Long id);

    int deleteByOrderId(@Param("orderId") Long orderId);

    // Utility operations
    Long countTotal();

    Long countByOrderId(@Param("orderId") Long orderId);

    Long countByBookId(@Param("bookId") Long bookId);

    Long countByStatus(@Param("status") String status);

    BigDecimal calculateTotalByOrderId(@Param("orderId") Long orderId);

    Integer getTotalQuantityByOrderId(@Param("orderId") Long orderId);

    boolean existsByOrderIdAndBookId(@Param("orderId") Long orderId, @Param("bookId") Long bookId);
}
