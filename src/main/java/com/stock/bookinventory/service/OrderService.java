package com.stock.bookinventory.service;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.stock.bookinventory.constants.AppConstants;
import com.stock.bookinventory.constants.ErrorCode;
import com.stock.bookinventory.converter.OrderConverter;
import com.stock.bookinventory.converter.OrderItemConverter;
import com.stock.bookinventory.dto.request.OrderItemRequestDTO;
import com.stock.bookinventory.dto.request.OrderRequestDTO;
import com.stock.bookinventory.dto.request.OrderStatusRequestDTO;
import com.stock.bookinventory.dto.response.BookResponseDTO;
import com.stock.bookinventory.dto.response.CustomerResponseDTO;
import com.stock.bookinventory.dto.response.OrderResponseDTO;
import com.stock.bookinventory.exception.GeneralException;
import com.stock.bookinventory.model.Order;
import com.stock.bookinventory.model.OrderItem;
import com.stock.bookinventory.model.OrderItemStatus;
import com.stock.bookinventory.model.OrderStatus;
import com.stock.bookinventory.repository.OrderItemRepository;
import com.stock.bookinventory.repository.OrderRepository;

@Service
public class OrderService {

	private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private BookService bookService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private TransactionTemplate transactionTemplate;

	public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {

		return transactionTemplate.execute(status -> {
			try {
				// Validate customer exists
				CustomerResponseDTO customerResponseDTO = customerService
						.findCustomerById(orderRequestDTO.getCustomerId());

				// Validate stock for all items (sorted by bookId to avoid deadlock)
				orderRequestDTO.getItems().stream().sorted(Comparator.comparing(OrderItemRequestDTO::getBookId))
						.forEach(item -> validateStock(item.getBookId(), item.getQuantity()));

				// Calculate total amount
				BigDecimal totalAmount = calculateItemTotal(orderRequestDTO);

				// Create order
				Order order = OrderConverter.toModel(orderRequestDTO);
				order.setStatus(OrderStatus.PENDING);
				order.setTotalAmount(totalAmount);
				orderRepository.insert(order);

				// Create order items and update stock
				List<OrderItem> orderItems = createOrderItems(order.getId(), orderRequestDTO);
				orderItems.forEach(item -> {
					orderItemRepository.insert(item);
					bookService.updateStock(item.getBookId(), item.getQuantity());
				});

				// Fetch the complete order with timestamps from database
				Order savedOrder = orderRepository.selectById(order.getId());

				// Fetch the complete order items with timestamps from database
				List<OrderItem> savedOrderItems = orderItemRepository.findByOrderId(order.getId());

				// Build response with complete data
				return buildOrderResponseDTO(savedOrder, customerResponseDTO, savedOrderItems);
			} catch (Exception e) {
				status.setRollbackOnly();
				logger.error("Error creating order: {}", e.getMessage(), e);
				throw new GeneralException(ErrorCode.ORDER_CREATION_FAILED,
						"Failed to create order: " + e.getMessage());
			}
		});
	}

	private BigDecimal calculateItemTotal(OrderRequestDTO orderRequestDTO) {
		if (orderRequestDTO.getItems() == null || orderRequestDTO.getItems().isEmpty()) {
			throw new GeneralException(ErrorCode.INVALID_ITEM_REQUEST, "Order must contain at least one item.");
		}

		return orderRequestDTO.getItems().stream().sorted(Comparator.comparing(OrderItemRequestDTO::getBookId))
				.map(this::calculateSubtotal).reduce(BigDecimal.ZERO, BigDecimal::add);
	}

	private BigDecimal calculateSubtotal(OrderItemRequestDTO item) {
		BookResponseDTO bookResponseDTO = bookService.findBookById(item.getBookId());
		return bookResponseDTO.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
	}

	private void validateStock(Long id, Integer requestQuantity) {
		BookResponseDTO bookResponseDTO = bookService.findBookById(id);
		if (bookResponseDTO.getStockQuantity() < requestQuantity) {
			throw new GeneralException(ErrorCode.INSUFFICIENT_STOCK, "Insufficient stock for book ID " + id
					+ ". Available: " + bookResponseDTO.getStockQuantity() + ", Requested: " + requestQuantity);
		}
	}

	private List<OrderItem> createOrderItems(Long orderId, OrderRequestDTO orderRequestDTO) {
		return orderRequestDTO.getItems().stream().sorted(Comparator.comparing(OrderItemRequestDTO::getBookId))
				.map(itemDTO -> {
					BookResponseDTO bookResponseDTO = bookService.findBookById(itemDTO.getBookId());
					OrderItem orderItem = new OrderItem();
					orderItem.setOrderId(orderId);
					orderItem.setBookId(itemDTO.getBookId());
					orderItem.setQuantity(itemDTO.getQuantity());
					orderItem.setSubtotal(calculateSubtotal(itemDTO));
					orderItem.setStatus(OrderItemStatus.PENDING); // Set default status
					return orderItem;
				}).toList();
	}

	private OrderResponseDTO buildOrderResponseDTO(Order order, CustomerResponseDTO customerResponseDTO,
			List<OrderItem> orderItems) {
		OrderResponseDTO orderResponseDTO = OrderConverter.toDTO(order, OrderItemConverter.toDTOList(orderItems));
		orderResponseDTO.setCustomerName(customerResponseDTO.getName());
		return orderResponseDTO;
	}

	@Async("OrderTimeoutExecutor")
	public void checkExpiredTimeoutOrder() {
		List<Order> ordersToExpire = orderRepository.findExpiredOrder(Integer.parseInt(AppConstants.PAGE_SIZE),
				Integer.parseInt(AppConstants.OFFSET));

		ordersToExpire.forEach(order -> {
			try {
				transactionTemplate.executeWithoutResult(status -> {
					try {
						// Lock the order for update
						Order updateOrder = orderRepository.getOrderForUpdate(order.getId());

						if (updateOrder == null) {
							throw new GeneralException(ErrorCode.RECORD_NOT_FOUND,
									"Order not found for ID " + order.getId());
						}

						// Parse current status and validate transition
						OrderStatus currentStatus = updateOrder.getStatus();
						currentStatus.validateTransition(OrderStatus.EXPIRED);

						// Update order status
						updateOrder.setStatus(OrderStatus.EXPIRED);

						int rowsUpdated = orderRepository.update(updateOrder);

						if (rowsUpdated == 0) {
							throw new GeneralException(ErrorCode.NO_ORDER_UPDATED,
									"Failed to update order status for order ID " + order.getId());
						}

						logger.info("Order {} expired successfully", order.getId());

					} catch (Exception e) {
						status.setRollbackOnly();
						logger.error("Error expiring order ID {}: {}", order.getId(), e.getMessage(), e);
						throw e;
					}
				});

				// Release stock after successful status update
				handleStockRelease(order);

			} catch (Exception e) {
				logger.error("Failed to process expired order ID {}: {}", order.getId(), e.getMessage(), e);
			}
		});
	}

	private void handleStockRelease(Order order) {
		try {
			// Retrieve all order items for this order
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());

			// Release stock for each item (sorted by bookId to avoid deadlock)
			orderItems.stream().sorted(Comparator.comparing(OrderItem::getBookId)).forEach(item -> {
				try {
					// Release stock by subtracting negative quantity (adding back)
					bookService.updateStock(item.getBookId(), -item.getQuantity());
					logger.info("Released {} units of book {} for expired order {}", item.getQuantity(),
							item.getBookId(), order.getId());
				} catch (Exception e) {
					logger.error("Failed to release stock for book {} in order {}: {}", item.getBookId(), order.getId(),
							e.getMessage(), e);
				}
			});
		} catch (Exception e) {
			logger.error("Error releasing stock for order {}: {}", order.getId(), e.getMessage(), e);
		}
	}

	public List<OrderResponseDTO> getAllOrders(int pageSize, int offset) {
		List<Order> orders = orderRepository.findAll(pageSize, offset);
		return orders.stream().map(order -> {
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
			return OrderConverter.toDTO(order, OrderItemConverter.toDTOList(orderItems));
		}).toList();
	}

	public List<OrderResponseDTO> getOrdersByCustomerId(Long customerId) {
		List<Order> orders = orderRepository.findByCustomerId(customerId);
		return orders.stream().map(order -> {
			List<OrderItem> orderItems = orderItemRepository.findByOrderId(order.getId());
			return OrderConverter.toDTO(order, OrderItemConverter.toDTOList(orderItems));
		}).toList();
	}

	public OrderResponseDTO getOrderById(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new GeneralException(ErrorCode.RECORD_NOT_FOUND,
						"Order with ID " + orderId + " does not exist."));

		List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
		CustomerResponseDTO customerResponseDTO = customerService.findCustomerById(order.getCustomerId());

		OrderResponseDTO orderResponseDTO = OrderConverter.toDTO(order, OrderItemConverter.toDTOList(orderItems));
		orderResponseDTO.setCustomerName(customerResponseDTO.getName());
		return orderResponseDTO;
	}

	public void deleteOrder(OrderRequestDTO orderRequestDTO) {
		Long orderId = orderRequestDTO.getId();

		transactionTemplate.executeWithoutResult(status -> {
			try {
				// Lock the order for update
				Order order = orderRepository.getOrderForUpdate(orderId);

				if (order == null) {
					throw new GeneralException(ErrorCode.RECORD_NOT_FOUND,
							"Order with ID " + orderId + " does not exist.");
				}

				// Get order items before deletion
				List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);

				// Delete order items first (foreign key constraint)
				orderItems.forEach(item -> orderItemRepository.deleteById(item.getId()));

				// Delete the order
				int rowsDeleted = orderRepository.deleteById(orderId);

				if (rowsDeleted == 0) {
					throw new GeneralException(ErrorCode.NO_ORDER_UPDATED, "Failed to delete order with ID " + orderId);
				}

				// Release stock for deleted order items (sorted by bookId to avoid deadlock)
				orderItems.stream().sorted(Comparator.comparing(OrderItem::getBookId)).forEach(item -> {
					bookService.releaseStock(item.getBookId(), item.getQuantity());
					logger.info("Released {} units of book {} for deleted order {}", item.getQuantity(),
							item.getBookId(), orderId);
				});

				logger.info("Order {} deleted successfully", orderId);

			} catch (Exception e) {
				status.setRollbackOnly();
				logger.error("Error deleting order ID {}: {}", orderId, e.getMessage(), e);
				throw e;
			}
		});
	}

	public void updateOrderStatus(OrderStatusRequestDTO orderStatusRequestDTO) {
		Long orderId = orderStatusRequestDTO.getId();
		OrderStatus newStatus = orderStatusRequestDTO.getStatus();

		transactionTemplate.executeWithoutResult(status -> {
			try {
				// Lock the order for update
				Order order = orderRepository.getOrderForUpdate(orderId);

				if (order == null) {
					throw new GeneralException(ErrorCode.RECORD_NOT_FOUND,
							"Order with ID " + orderId + " does not exist.");
				}

				// Validate status transition
				OrderStatus currentStatus = order.getStatus();
				try {
					currentStatus.validateTransition(newStatus);
				} catch (IllegalStateException e) {
					throw new GeneralException(ErrorCode.INVALID_STATUS_TRANSITION, e.getMessage());
				}

				// Update order status
				order.setStatus(newStatus);

				int rowsUpdated = orderRepository.update(order);

				if (rowsUpdated == 0) {
					throw new GeneralException(ErrorCode.NO_ORDER_UPDATED,
							"Failed to update order status for order ID " + orderId);
				}

				// If order is cancelled or expired, release stock
				if (newStatus == OrderStatus.CANCELLED || newStatus == OrderStatus.EXPIRED) {
					handleStockRelease(order);
				}

				logger.info("Order {} status updated from {} to {}", orderId, currentStatus, newStatus);

			} catch (Exception e) {
				status.setRollbackOnly();
				logger.error("Error updating order status for ID {}: {}", orderId, e.getMessage(), e);
				throw e;
			}
		});
	}
}
