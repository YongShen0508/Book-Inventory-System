package com.stock.bookinventory.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class BookRequestDTO {
	@NotNull(groups = UpdateValidation.class, message = "Book ID is required for update")
	@Min(value = 1, groups = UpdateValidation.class, message = "Book ID must be at least 1")
	private Long id;

	@NotBlank(message = "Title must not be blank")
	@Size(max = 128, message = "Title must not exceed 255 characters")
	private String title;

	@NotBlank(message = "Author must not be blank")
	@Size(max = 128, message = "Author name must not exceed 255 characters")
	private String author;

	@NotBlank(message = "Genre must not be blank")
	@Size(max = 100, message = "Genre must not exceed 100 characters")
	private String genre;

	@NotNull(message = "Publication date must not be null")
	@Past(message = "Publication date must be in the past")
	private Date publicationDate;

	@NotNull(message = "Price must not be null")
	@DecimalMin(value = "0.01", message = "Price must be at least 0.01")
	@DecimalMax(value = "99999.99", message = "Price must not exceed 99999.99")
	private BigDecimal price;

	@NotNull(message = "Stock quantity must not be null")
	@Min(value = 0, message = "Stock quantity must be non-negative")
	@Max(value = 100000, message = "Stock quantity must not exceed 100000")
	private Integer stockQuantity;
}
