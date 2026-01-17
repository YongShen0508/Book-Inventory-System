package com.stock.bookinventory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.stock.bookinventory.mapper")
public class BookInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookInventoryApplication.class, args);
	}

}
