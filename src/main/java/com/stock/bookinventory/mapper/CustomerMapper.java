package com.stock.bookinventory.mapper;

import com.stock.bookinventory.model.Customer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CustomerMapper {

    // Insert
    int insert(Customer customer);

    // Find operations
    List<Customer> findAll();

    Customer findById(@Param("id") Long id);

    Customer selectById(@Param("id") Long id);

    Customer getCustomerForUpdate(@Param("id") Long id);

    Customer findByEmail(@Param("email") String email);

    Customer findByPhone(@Param("phone") String phone);

    List<Customer> findByKeyword(@Param("keyword") String keyword);

    List<Customer> findByCriteria(@Param("name") String name,
                                   @Param("email") String email,
                                   @Param("phone") String phone,
                                   @Param("address") String address);

    // Update operations
    int update(Customer customer);

    // Delete operations
    int deleteById(@Param("id") Long id);

    // Utility operations
    Long countTotal();

    boolean existsByEmail(@Param("email") String email);

    boolean existsByPhone(@Param("phone") String phone);
}
