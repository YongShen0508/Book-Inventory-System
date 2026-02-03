package com.stock.bookinventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stock.bookinventory.model.Customer;

@Mapper
public interface CustomerMapper {

	// Insert
	int insert(Customer customer);

	// Find operations
	List<Customer> findAll(Integer pageSize, Integer offset);

	<Optional> Customer findById(@Param("id") Long id);

	Customer selectById(@Param("id") Long id);

	Customer getCustomerForUpdate(@Param("id") Long id);

	Customer findByEmail(@Param("email") String email);

	Customer findByPhone(@Param("phone") String phone);

	List<Customer> findByKeyword(@Param("keyword") String keyword, Integer pageSize, Integer offset);

	// Update operations
	int update(Customer customer);

	// Delete operations
	int deleteById(@Param("id") Long id);

	// Utility operations
	Long countTotal();

	boolean existsByEmail(@Param("email") String email);

	boolean existsByPhone(@Param("phone") String phone);
}
