package com.stock.bookinventory.dto.response;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class BookResponseDTO {
	private Long id;
	private String title;
	private String author;
	private String isbn;
	private BigDecimal price;
	private Integer stockQuantity;
	private Date createdAt;
	private Date updatedAt;

}
