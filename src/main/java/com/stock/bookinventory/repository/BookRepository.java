package com.stock.bookinventory.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.stock.bookinventory.dto.request.BookRequestByCriteriaDTO;
import com.stock.bookinventory.mapper.BookMapper;
import com.stock.bookinventory.model.Book;

@Repository
public class BookRepository {

	private final BookMapper bookMapper;

	public BookRepository(BookMapper bookMapper) {
		this.bookMapper = bookMapper;
	}

	public void insert(Book book) {
		bookMapper.insert(book);
	}

	public List<Book> findAll(Integer pageSize, Integer offset) {
		return bookMapper.findAll(pageSize, offset);
	}

	public Optional<Book> findById(Long id) {
		return Optional.ofNullable(bookMapper.findById(id));
	}

	public Book selectById(Long id) {
		return bookMapper.selectById(id);
	}

	public Book selectBookForUpdate(Long id) {
		return bookMapper.selectBookForUpdate(id);
	}

	public List<Book> findByKeyword(String keyword, Integer pageSize, Integer offset) {
		return bookMapper.findByKeyword(keyword, pageSize, offset);
	}

	public List<Book> findByCriteria(BookRequestByCriteriaDTO criteria, Integer pageSize, Integer offset) {
		return bookMapper.findByCriteria(criteria, pageSize, offset);
	}

	public int update(Book book) {
		return bookMapper.update(book);
	}

	public void deleteById(Long id) {
		bookMapper.deleteById(id);
	}

}
