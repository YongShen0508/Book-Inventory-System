package com.stock.bookinventory.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {

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
