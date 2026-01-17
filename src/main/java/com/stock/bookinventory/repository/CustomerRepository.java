package com.stock.bookinventory.repository;

import com.stock.bookinventory.mapper.CustomerMapper;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerRepository {

    private final CustomerMapper customerMapper;

    public CustomerRepository(CustomerMapper customerMapper){this.customerMapper = customerMapper;}

}
