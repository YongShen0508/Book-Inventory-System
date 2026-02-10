package com.stock.bookinventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.stock.bookinventory.mapper.CustomerMapper;
import com.stock.bookinventory.model.Customer;

@Repository
public class CustomerRepository {

	private final CustomerMapper customerMapper;

	public CustomerRepository(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}

	public int insert(Customer customer) {
		return customerMapper.insert(customer);
	}

	public List<Customer> findAll(Integer pageSize, Integer offset) {
		return customerMapper.findAll(pageSize, offset);
	}

	public Optional<Customer> findById(Long id) {
		return Optional.ofNullable(customerMapper.findById(id));
	}

	public Customer selectById(Long id) {
		return customerMapper.selectById(id);
	}

	public Customer getCustomerForUpdate(Long id) {
		return customerMapper.getCustomerForUpdate(id);
	}

	public Customer findByEmail(String email) {
		return customerMapper.findByEmail(email);
	}

	public List<Customer> findByKeyword(String keyword, Integer pageSize, Integer offset) {
		return customerMapper.findByKeyword(keyword, pageSize, offset);
	}

	public int update(Customer customer) {
		return customerMapper.update(customer);
	}

	public int deleteById(Long id) {
		return customerMapper.deleteById(id);
	}

}
