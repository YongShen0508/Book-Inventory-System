package com.stock.bookinventory.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private Long id;
    private String name;
    private String email;
    private String phone;
    private String address;
    private Date createdAt;
    private Date updatedAt;
}
