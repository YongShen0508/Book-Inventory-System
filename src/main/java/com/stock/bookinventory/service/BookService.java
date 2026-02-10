package com.stock.bookinventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stock.bookinventory.constants.ErrorCode;
import com.stock.bookinventory.converter.BookConverter;
import com.stock.bookinventory.dto.request.BookRequestByCriteriaDTO;
import com.stock.bookinventory.dto.request.BookRequestDTO;
import com.stock.bookinventory.dto.response.BookResponseDTO;
import com.stock.bookinventory.exception.GeneralException;
import com.stock.bookinventory.model.Book;
import com.stock.bookinventory.repository.BookRepository;

@Service
public class BookService {

	private final BookRepository bookRepository;

	@Autowired
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	public List<BookResponseDTO> getAllBooks(int pageSize, int offset) {
		List<Book> books = bookRepository.findAll(pageSize, offset);
		return books.stream().map(BookConverter::toDTO).toList();
	}

	@Transactional
	public BookResponseDTO createBook(BookRequestDTO bookRequestDTO) {
		Book book = BookConverter.toModel(bookRequestDTO);
		bookRepository.insert(book);
		return BookConverter.toDTO(bookRepository.selectById(book.getId()));
	}

	@Transactional
	public void deleteBook(BookRequestDTO bookRequestDTO) {
		if (bookRepository.findById(bookRequestDTO.getId()).isEmpty()) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND,
					"Book with ID " + bookRequestDTO.getId() + " does not exist.");
		}
		bookRepository.deleteById(bookRequestDTO.getId());
	}

	@Transactional
	public void updateBook(BookRequestDTO bookRequestDTO) {
		Long bookId = bookRequestDTO.getId();

		// Use SELECT FOR UPDATE to acquire a pessimistic lock and prevent concurrent
		// modifications
		Book existingBook = bookRepository.selectBookForUpdate(bookId);
		if (existingBook == null) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Book with ID " + bookId + " does not exist.");
		}

		// Convert DTO to model and perform update
		Book book = BookConverter.toModel(bookRequestDTO);

		bookRepository.update(book);
	}

	public BookResponseDTO getBookById(Long id) {
		return bookRepository.findById(id).map(BookConverter::toDTO).orElseThrow(
				() -> new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Book with ID " + id + " does not exist."));
	}

	public BookResponseDTO findBookById(Long id) {
		Book book = bookRepository.findById(id).orElseThrow(
				() -> new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Book with ID " + id + " does not exist."));
		return BookConverter.toDTO(book);
	}

	@Transactional
	public void updateStock(Long bookId, Integer quantity) {
		// Use SELECT FOR UPDATE to acquire a pessimistic lock
		Book book = bookRepository.selectBookForUpdate(bookId);
		if (book == null) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Book with ID " + bookId + " does not exist.");
		}

		int newStock = book.getStockQuantity() - quantity;
		if (newStock < 0) {
			throw new GeneralException(ErrorCode.INSUFFICIENT_STOCK, "Insufficient stock for book ID " + bookId
					+ ". Available: " + book.getStockQuantity() + ", Requested: " + quantity);
		}

		book.setStockQuantity(newStock);
		bookRepository.update(book);
	}

	@Transactional
	public void releaseStock(Long bookId, Integer quantity) {
		// Use SELECT FOR UPDATE to acquire a pessimistic lock
		Book book = bookRepository.selectBookForUpdate(bookId);
		if (book == null) {
			throw new GeneralException(ErrorCode.RECORD_NOT_FOUND, "Book with ID " + bookId + " does not exist.");
		}

		int newStock = book.getStockQuantity() + quantity;
		book.setStockQuantity(newStock);
		bookRepository.update(book);
	}

	public List<BookResponseDTO> searchBooksByKeyword(String keyword, int pageSize, int offset) {
		List<Book> books = bookRepository.findByKeyword(keyword, pageSize, offset);
		return books.stream().map(BookConverter::toDTO).toList();
	}

	public List<BookResponseDTO> searchBooksByCriteria(BookRequestByCriteriaDTO bookRequestByCriteriaDTO, int pageSize,
			int offset) {
		List<Book> books = bookRepository.findByCriteria(bookRequestByCriteriaDTO, pageSize, offset);
		return books.stream().map(BookConverter::toDTO).toList();
	}

}
