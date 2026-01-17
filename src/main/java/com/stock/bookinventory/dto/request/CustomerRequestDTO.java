package com.stock.bookinventory.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CustomerRequestDTO {

	@NotNull(message = "Customer ID is required for update")
	@Min(value = 1, message = "Customer ID must be at least 1")
	private Long id;

	@NotBlank(message = "Customer name must not be blank")
	@Size(max = 128, message = "Customer name must not exceed 255 characters")
	private String name;

	@NotBlank(message = "Email must not be blank")
	@Email(message = "Email must be a valid email address")
	@Size(max = 128, message = "Email must not exceed 255 characters")
	private String email;

	@NotBlank(message = "Phone number must not be blank")
	@Pattern(regexp = "^[0-9+\\-\\s()]+$", message = "Phone number must contain only valid characters")
	@Size(max = 20, message = "Phone number must not exceed 20 characters")
	private String phone;
}
