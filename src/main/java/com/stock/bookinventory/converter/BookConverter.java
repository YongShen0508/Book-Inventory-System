package com.stock.bookinventory.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.stock.bookinventory.dto.request.BookRequestDTO;
import com.stock.bookinventory.dto.response.BookResponseDTO;
import com.stock.bookinventory.model.Book;

public class BookConverter {

	public static BookResponseDTO toDTO(Book book) {
		if (book == null) {
			return new BookResponseDTO();
		}

		BookResponseDTO dto = new BookResponseDTO();
		dto.setId(book.getId());
		dto.setTitle(book.getTitle());
		dto.setAuthor(book.getAuthor());
		// Note: Book entity doesn't have isbn field, so leaving it null
		// You may need to add isbn to Book entity or handle this mapping differently
		dto.setGenre(book.getGenre());
		dto.setPrice(book.getPrice());
		dto.setStockQuantity(book.getStockQuantity());
		dto.setPublishedDate(book.getPublicationDate());
		dto.setCreatedAt(book.getCreatedAt());
		dto.setUpdatedAt(book.getUpdatedAt());

		return dto;
	}

	public static Book toModel(BookRequestDTO dto) {
		if (dto == null) {
			return new Book();
		}

		Book book = new Book();
		book.setTitle(dto.getTitle());
		book.setAuthor(dto.getAuthor());
		book.setGenre(dto.getGenre());
		book.setPrice(dto.getPrice());
		book.setStockQuantity(dto.getStockQuantity());
		book.setPublicationDate(dto.getPublicationDate());

		return book;
	}

	public static List<BookResponseDTO> toDTOList(List<Book> books) {
		if (books == null || books.isEmpty()) {
			return new ArrayList<>();
		}

		return books.stream().map(BookConverter::toDTO).collect(Collectors.toList());
	}
}
