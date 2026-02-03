package com.stock.bookinventory.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.stock.bookinventory.dto.request.BookRequestByCriteriaDTO;
import com.stock.bookinventory.model.Book;

@Mapper
public interface BookMapper {

	// Insert
	int insert(Book book);

	// Find operations
	List<Book> findAll(Integer pageSize, Integer offset);

	<Optional> Book findById(@Param("id") Long id);

	Book selectById(@Param("id") Long id);

	Book selectBookForUpdate(@Param("id") Long id);

	List<Book> findByKeyword(@Param("keyword") String keyword, Integer pageSize, Integer offset);

	List<Book> findByCriteria(BookRequestByCriteriaDTO criteria, Integer pageSize, Integer offset);

	List<Book> findLowStockBooks(@Param("threshold") Integer threshold);

	// Update operations
	int update(Book book);

	int deductStock(@Param("bookId") Long bookId, @Param("quantity") Integer quantity);

	int addStock(@Param("bookId") Long bookId, @Param("quantity") Integer quantity);

	// Delete operations
	int deleteById(@Param("id") Long id);

	// Utility operations
	boolean checkStockAvailability(@Param("bookId") Long bookId, @Param("quantity") Integer quantity);

	Integer getCurrentStock(@Param("bookId") Long bookId);

}
