package com.stock.bookinventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.stock.bookinventory.constants.AppConstants;
import com.stock.bookinventory.dto.request.CustomerRequestDTO;
import com.stock.bookinventory.dto.response.ApiResponse;
import com.stock.bookinventory.dto.response.CustomerResponseDTO;
import com.stock.bookinventory.service.CustomerService;

@RestController
@RequestMapping("/customers")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}

	@PostMapping("/createCustomer")
	public ApiResponse<CustomerResponseDTO> createCustomer(CustomerRequestDTO customerRequestDTO) {
		CustomerResponseDTO createdCustomer = customerService.createCustomer(customerRequestDTO);
		return ApiResponse.success("Customer created successfully", createdCustomer);
	}

	@DeleteMapping
	public ApiResponse<Void> deleteCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
		customerService.deleteCustomer(customerRequestDTO);
		return ApiResponse.success("Customer deleted successfully");
	}

	@PutMapping
	public ApiResponse<Void> updateCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
		customerService.updateCustomer(customerRequestDTO);
		return ApiResponse.success("Customer updated successfully");
	}

	@GetMapping
	public ApiResponse<List<CustomerResponseDTO>> getAllCustomers(
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConstants.OFFSET) int offset) {
		List<CustomerResponseDTO> customers = customerService.getAllCustomers(pageSize, offset);
		return ApiResponse.success("Customers retrieved successfully", customers);
	}

	@GetMapping("/{id}")
	public ApiResponse<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
		CustomerResponseDTO customer = customerService.getCustomerById(id);
		return ApiResponse.success("Customer retrieved successfully", customer);
	}

	@GetMapping("/{email}")
	public ApiResponse<CustomerResponseDTO> getCustomerByEmail(@PathVariable String email) {
		CustomerResponseDTO customer = customerService.getCustomerByEmail(email);
		return ApiResponse.success("Customer retrieved successfully", customer);
	}

}
