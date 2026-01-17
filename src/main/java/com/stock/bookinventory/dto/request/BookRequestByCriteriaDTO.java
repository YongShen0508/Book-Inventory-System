package com.stock.bookinventory.dto.request;

import java.math.BigDecimal;
import java.util.Date;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;

@Data
public class BookRequestByCriteriaDTO {
	private String title;

	private String author;

	private String genre;

	@DecimalMin(value = "0.01", inclusive = true, message = "Minimum price must be non-negative")
	private BigDecimal minPrice;

	@DecimalMax(value = "0.01", message = "Maximum price must be greater than or equal to minimum price")
	private BigDecimal maxPrice;

	@PastOrPresent(message = "Start date must be in the past or present")
	private Date startDate;

	@PastOrPresent(message = "End date must be in the past or present")
	private Date endDate;

	@AssertTrue(message = "Maximum price must be greater than or equal to minimum price")
	private boolean isValidPriceRange() {
		if (minPrice != null && maxPrice != null) {
			return maxPrice.compareTo(minPrice) >= 0;
		}
		return true; // If one of them is null, we consider it valid
	}

}
