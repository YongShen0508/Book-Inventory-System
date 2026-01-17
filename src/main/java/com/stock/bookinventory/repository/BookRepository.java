package com.stock.bookinventory.repository;

import com.stock.bookinventory.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepository {

    private final BookMapper bookMapper;

    public BookRepository(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }


}
