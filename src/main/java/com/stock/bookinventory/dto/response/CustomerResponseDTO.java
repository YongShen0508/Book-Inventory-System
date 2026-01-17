package com.stock.bookinventory.dto.response;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerResponseDTO {

	private Long id;
	private String name;
	private String email;
	private String phone;
	private Date createdAt;
	private Date updatedAt;
}
