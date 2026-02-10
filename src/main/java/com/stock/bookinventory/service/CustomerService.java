package com.stock.bookinventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.bookinventory.constants.ErrorCode;
import com.stock.bookinventory.converter.CustomerConverter;
import com.stock.bookinventory.dto.request.CustomerRequestDTO;
import com.stock.bookinventory.dto.response.CustomerResponseDTO;
import com.stock.bookinventory.exception.GeneralException;
import com.stock.bookinventory.model.Customer;
import com.stock.bookinventory.repository.CustomerRepository;

@Service
public class CustomerService {

	private final CustomerRepository customerRepository;

	@Autowired
	public CustomerService(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	public List<CustomerResponseDTO> getAllCustomers(int pageSize, int offset) {
		List<Customer> customers = customerRepository.findAll(pageSize, offset);
		return customers.stream().map(CustomerConverter::toDTO).toList();
	}

	public CustomerResponseDTO getCustomerById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.map(CustomerConverter::toDTO).orElse(null);
	}

	public CustomerResponseDTO findCustomerById(Long id) {
		Optional<Customer> customer = customerRepository.findById(id);
		return customer.map(CustomerConverter::toDTO).orElseThrow(
				() -> new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Customer with ID " + id + " not found"));
	}

	public CustomerResponseDTO getCustomerByEmail(String email) {
		Customer customer = customerRepository.findByEmail(email);
		if (customer == null) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Customer with email " + email + " not found");
		}
		return CustomerConverter.toDTO(customer);
	}

	public List<CustomerResponseDTO> searchCustomers(String keyword, int pageSize, int offset) {
		List<Customer> customers = customerRepository.findByKeyword(keyword, pageSize, offset);
		return customers.stream().map(CustomerConverter::toDTO).toList();
	}

	@Transactional
	public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
		// Check if email already exists
		Customer existingCustomer = customerRepository.findByEmail(customerRequestDTO.getEmail());
		if (existingCustomer != null) {
			throw new GeneralException(ErrorCode.DUPLICATE_EMAIL,
					"Customer with email '" + customerRequestDTO.getEmail() + "' already exists.");
		}

		Customer customer = CustomerConverter.toModel(customerRequestDTO);
		customerRepository.insert(customer);
		return CustomerConverter.toDTO(customerRepository.selectById(customer.getId()));
	}

	@Transactional
	public void updateCustomer(CustomerRequestDTO customerRequestDTO) {
		Customer existingCustomer = customerRepository.getCustomerForUpdate(customerRequestDTO.getId());
		if (existingCustomer == null) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND,
					"Customer with ID " + customerRequestDTO.getId() + " does not exist.");
		}

		// Check if the new email already exists (and belongs to a different customer)
		Customer customerWithEmail = customerRepository.findByEmail(customerRequestDTO.getEmail());
		if (customerWithEmail != null && !customerWithEmail.getId().equals(customerRequestDTO.getId())) {
			throw new GeneralException(ErrorCode.DUPLICATE_EMAIL,
					"Email '" + customerRequestDTO.getEmail() + "' is already in use by another customer.");
		}

		Customer customer = CustomerConverter.toModel(customerRequestDTO);
		customerRepository.update(customer);
	}

	@Transactional
	public void deleteCustomer(CustomerRequestDTO customerRequestDTO) {
		Customer existingCustomer = customerRepository.getCustomerForUpdate(customerRequestDTO.getId());
		if (existingCustomer == null) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND,
					"Customer with ID " + customerRequestDTO.getId() + " does not exist.");
		}
		customerRepository.deleteById(customerRequestDTO.getId());
	}
}
