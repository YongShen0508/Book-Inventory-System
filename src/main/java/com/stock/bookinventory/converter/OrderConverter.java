package com.stock.bookinventory.converter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.stock.bookinventory.constants.AppConstants;
import com.stock.bookinventory.dto.request.OrderRequestDTO;
import com.stock.bookinventory.dto.response.OrderResponseDTO;
import com.stock.bookinventory.model.Order;

public class OrderConverter {

	public static OrderResponseDTO toDTO(Order order) {
		if (order == null) {
			return new OrderResponseDTO();
		}

		OrderResponseDTO dto = new OrderResponseDTO();
		dto.setId(order.getId());
		dto.setCustomerId(order.getCustomerId());
		dto.setOrderDate(order.getOrderDate());
		dto.setStatus(order.getStatus());
		dto.setTotalAmount(order.getTotalAmount());
		dto.setOrderAt(order.getOrderAt());
		dto.setExpiresAt(order.getExpiresAt());
		dto.setCreatedAt(order.getCreatedAt());
		dto.setUpdatedAt(order.getUpdatedAt());

		return dto;
	}

	public static OrderResponseDTO toDTO(Order order,
			List<com.stock.bookinventory.dto.response.OrderItemResponseDTO> orderItems) {
		OrderResponseDTO dto = toDTO(order);
		dto.setOrderItems(orderItems);
		return dto;
	}

	public static Order toModel(OrderRequestDTO dto) {
		if (dto == null) {
			return new Order();
		}

		Order order = new Order();
		order.setId(dto.getId());
		order.setCustomerId(dto.getCustomerId());
		order.setOrderAt(new Date());
		order.setExpiresAt(new Date(AppConstants.EXPIRED_DATE));
		return order;
	}

	public static List<OrderResponseDTO> toDTOList(List<Order> orders) {
		if (orders == null || orders.isEmpty()) {
			return new ArrayList<>();
		}

		return orders.stream().map(OrderConverter::toDTO).collect(Collectors.toList());
	}
}
