package com.stock.bookinventory.acpect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stock.bookinventory.constants.AppConstants;
import com.stock.bookinventory.model.Book;
import com.stock.bookinventory.repository.BookRepository;

@Aspect
@Component
public class LowStockAspect {

	private static final Logger logger = LoggerFactory.getLogger(LowStockAspect.class);

	@Autowired
	private BookRepository bookRepository;

	/**
	 * Monitor stock updates in BookService.updateStock method Triggers alert when
	 * stock falls below threshold
	 */
	@AfterReturning("execution(* com.stock.bookinventory.service.BookService.updateStock(..))")
	public void checkLowStockAfterUpdate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args.length >= 1 && args[0] instanceof Long) {
			Long bookId = (Long) args[0];
			checkAndAlertLowStock(bookId);
		}
	}

	/**
	 * Monitor stock updates in BookService.releaseStock method
	 */
	@AfterReturning("execution(* com.stock.bookinventory.service.BookService.releaseStock(..))")
	public void checkLowStockAfterRelease(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args.length >= 1 && args[0] instanceof Long) {
			Long bookId = (Long) args[0];
			checkAndAlertLowStock(bookId);
		}
	}

	/**
	 * Monitor book updates in BookService.updateBook method
	 */
	@AfterReturning("execution(* com.stock.bookinventory.service.BookService.updateBook(..))")
	public void checkLowStockAfterBookUpdate(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		if (args.length >= 1) {
			// Extract book ID from BookRequestDTO or Book object
			try {
				Object arg = args[0];
				if (arg != null) {
					// Use reflection to get ID field
					Long bookId = extractBookId(arg);
					if (bookId != null) {
						checkAndAlertLowStock(bookId);
					}
				}
			} catch (Exception e) {
				logger.warn("Failed to extract book ID for low stock check: {}", e.getMessage());
			}
		}
	}

	/**
	 * Check stock level and alert if below threshold
	 */
	private void checkAndAlertLowStock(Long bookId) {
		try {
			Book book = bookRepository.selectById(bookId);
			if (book != null) {
				Integer currentStock = book.getStockQuantity();
				if (currentStock != null && currentStock <= AppConstants.STOCK_LOW_THRESHOLD) {
					logLowStockAlert(book, currentStock);

					// Additional actions can be added here:
					// - Send email notification
					// - Trigger reorder process
					// - Update database flag
					// - Publish event to message queue
				}
			}
		} catch (Exception e) {
			logger.error("Error checking low stock for book ID {}: {}", bookId, e.getMessage(), e);
		}
	}

	/**
	 * Log low stock alert with details
	 */
	private void logLowStockAlert(Book book, Integer currentStock) {
		logger.warn("╔════════════════════════════════════════════════════════════════╗");
		logger.warn("║               LOW STOCK ALERT                                 ║");
		logger.warn("╠════════════════════════════════════════════════════════════════╣");
		logger.warn("║ Book ID:     {}", String.format("%-48s", book.getId()) + "║");
		logger.warn("║ Title:       {}", String.format("%-48s", truncate(book.getTitle(), 48)) + "║");
		logger.warn("║ Author:      {}", String.format("%-48s", truncate(book.getAuthor(), 48)) + "║");
		logger.warn("║ Genre:       {}", String.format("%-48s", truncate(book.getGenre(), 48)) + "║");
		logger.warn("║ Current Stock: {}", String.format("%-44d", currentStock) + "║");
		logger.warn("║ Threshold:   {}", String.format("%-48d", AppConstants.STOCK_LOW_THRESHOLD) + "║");

		if (currentStock == 0) {
			logger.warn("║ STATUS:      OUT OF STOCK - IMMEDIATE ACTION REQUIRED!      ║");
		} else if (currentStock <= AppConstants.STOCK_LOW_THRESHOLD / 2) {
			logger.warn("║ STATUS:      CRITICALLY LOW - URGENT REORDER NEEDED!        ║");
		} else {
			logger.warn("║ STATUS:      LOW STOCK - REORDER RECOMMENDED                ║");
		}

		logger.warn("╚════════════════════════════════════════════════════════════════╝");
	}

	/**
	 * Extract book ID from DTO or entity object using reflection
	 */
	private Long extractBookId(Object obj) {
		try {
			// Try to get getId method
			var method = obj.getClass().getMethod("getId");
			Object result = method.invoke(obj);
			if (result instanceof Long) {
				return (Long) result;
			}
		} catch (Exception e) {
			logger.debug("Could not extract book ID from object: {}", e.getMessage());
		}
		return null;
	}

	/**
	 * Truncate string to specified length
	 */
	private String truncate(String str, int maxLength) {
		if (str == null) {
			return "";
		}
		return str.length() > maxLength ? str.substring(0, maxLength - 3) + "..." : str;
	}
}
