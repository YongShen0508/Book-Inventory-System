package com.stock.bookinventory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.stock.bookinventory.dto.request.OrderRequestDTO;
import com.stock.bookinventory.dto.request.OrderStatusRequestDTO;
import com.stock.bookinventory.dto.response.ApiResponse;
import com.stock.bookinventory.dto.response.OrderResponseDTO;
import com.stock.bookinventory.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

	private final OrderService orderService;

	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@PostMapping("/createOrder")
	public ApiResponse<OrderResponseDTO> createOrder(OrderRequestDTO orderRequestDTO) {
		OrderResponseDTO createdOrder = orderService.createOrder(orderRequestDTO);
		return ApiResponse.success("Order created successfully", createdOrder);
	}

	@DeleteMapping("/deleteOrder")
	public ApiResponse<OrderResponseDTO> deleteOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
		orderService.deleteOrder(orderRequestDTO);
		return ApiResponse.success("Order deleted successfully");
	}

	@PutMapping("/updateOrderStatus")
	public ApiResponse<OrderResponseDTO> updateOrderStatus(@RequestBody OrderStatusRequestDTO orderStatusRequestDTO) {
		orderService.updateOrderStatus(orderStatusRequestDTO);
		return ApiResponse.success("Order status updated successfully");
	}

	@GetMapping("/customer/{customerId}")
	public ApiResponse<List<OrderResponseDTO>> getOrderByCustomerId(@PathVariable Long customerId) {
		List<OrderResponseDTO> orders = orderService.getOrdersByCustomerId(customerId);
		return ApiResponse.success("Orders retrieved successfully", orders);
	}

	@GetMapping("/order/{orderId}")
	public ApiResponse<OrderResponseDTO> getOrderById(@PathVariable Long orderId) {
		OrderResponseDTO order = orderService.getOrderById(orderId);
		return ApiResponse.success("Order retrieved successfully", order);
	}

}
