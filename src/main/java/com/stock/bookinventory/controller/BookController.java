package com.stock.bookinventory.controller;

import java.util.List;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.stock.bookinventory.constants.AppConstants;
import com.stock.bookinventory.dto.request.*;
import com.stock.bookinventory.dto.response.ApiResponse;
import com.stock.bookinventory.dto.response.BookResponseDTO;
import com.stock.bookinventory.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {

	private final BookService bookService;

	public BookController(BookService bookService) {
		this.bookService = bookService;
	}

	@PostMapping("/createBook")
	public ApiResponse<BookResponseDTO> createBook(
			@Validated(CreateValidation.class) @RequestBody BookRequestDTO bookRequestDTO) {
		BookResponseDTO createdBook = bookService.createBook(bookRequestDTO);
		return ApiResponse.success("Book created successfully", createdBook);
	}

	@DeleteMapping
	public ApiResponse<Void> deleteBook(@Validated(DeleteValidation.class) @RequestBody BookRequestDTO bookRequestDTO) {
		bookService.deleteBook(bookRequestDTO);
		return ApiResponse.success("Book deleted successfully");
	}

	@PutMapping
	public ApiResponse<Void> updateBook(@Validated(UpdateValidation.class) @RequestBody BookRequestDTO bookRequestDTO) {
		bookService.updateBook(bookRequestDTO);
		return ApiResponse.success("Book updated successfully");
	}

	@GetMapping
	public ApiResponse<List<BookResponseDTO>> getAllBook(
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConstants.OFFSET) int offset) {
		List<BookResponseDTO> book = bookService.getAllBooks(pageSize, offset);
		return ApiResponse.success("Books retrieved successfully", book);
	}

	@GetMapping("/searchByKeyword")
	public ApiResponse<List<BookResponseDTO>> searchBooksByKeyword(@RequestParam String keyword,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConstants.OFFSET) int offset) {
		List<BookResponseDTO> books = bookService.searchBooksByKeyword(keyword, pageSize, offset);
		return ApiResponse.success("Books retrieved successfully", books);
	}

	@GetMapping("/searchByCriteria")
	public ApiResponse<List<BookResponseDTO>> searchBooksByCriteria(
			@Valid @ModelAttribute BookRequestByCriteriaDTO bookRequestByCriteriaDTO,
			@RequestParam(defaultValue = AppConstants.PAGE_SIZE) int pageSize,
			@RequestParam(defaultValue = AppConstants.OFFSET) int offset) {
		List<BookResponseDTO> books = bookService.searchBooksByCriteria(bookRequestByCriteriaDTO, pageSize, offset);
		return ApiResponse.success("Books retrieved successfully", books);
	}

	@GetMapping("/{id}")
	public ApiResponse<BookResponseDTO> getBookById(@PathVariable Long id) {
		BookResponseDTO book = bookService.getBookById(id);
		return ApiResponse.success("Book retrieved successfully", book);
	}

}