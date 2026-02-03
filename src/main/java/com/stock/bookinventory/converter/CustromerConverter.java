package com.stock.bookinventory.converter;

import com.stock.bookinventory.dto.request.CustomerRequestDTO;
import com.stock.bookinventory.dto.response.CustomerResponseDTO;
import com.stock.bookinventory.model.Customer;

public class CustromerConverter {

	public static CustomerResponseDTO toDTO(Customer customer) {
		if (customer == null) {
			return null;
		}

		CustomerResponseDTO dto = new CustomerResponseDTO();
		dto.setId(customer.getId());
		dto.setName(customer.getName());
		dto.setEmail(customer.getEmail());
		dto.setPhone(customer.getPhone());
		dto.setCreatedAt(customer.getCreatedAt());
		dto.setUpdatedAt(customer.getUpdatedAt());

		return dto;
	}

	public static Customer toModel(CustomerRequestDTO dto) {
		if (dto == null) {
			return null;
		}

		Customer customer = new Customer();
		customer.setId(dto.getId());
		customer.setName(dto.getName());
		customer.setEmail(dto.getEmail());
		customer.setPhone(dto.getPhone());

		return customer;
	}
}
