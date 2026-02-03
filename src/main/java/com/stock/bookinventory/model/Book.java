package com.stock.bookinventory.model;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

	@Serial
	private static final long serialVersionUID = 586816613760341306L;

	private Long id;
	private String title;
	private String author;
	private String genre;
	private Date publicationDate;
	private BigDecimal price;
	private Integer stockQuantity;
	private Date createdAt;
	private Date updatedAt;
}
