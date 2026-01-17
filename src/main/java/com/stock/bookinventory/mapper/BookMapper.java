package com.stock.bookinventory.mapper;

import com.stock.bookinventory.model.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

@Mapper
public interface BookMapper {

    // Insert
    int insert(Book book);

    // Find operations
    List<Book> findAll();

    Book findById(@Param("id") Long id);

    Book selectById(@Param("id") Long id);

    Book getBookForUpdate(@Param("id") Long id);

    List<Book> findByKeyword(@Param("keyword") String keyword);

    List<Book> findByCriteria(@Param("title") String title,
                               @Param("author") String author,
                               @Param("genre") String genre,
                               @Param("minPrice") BigDecimal minPrice,
                               @Param("maxPrice") BigDecimal maxPrice,
                               @Param("minStock") Integer minStock,
                               @Param("maxStock") Integer maxStock);

    List<Book> findByGenre(@Param("genre") String genre);

    List<Book> findByAuthor(@Param("author") String author);

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

    Long countTotal();
}
