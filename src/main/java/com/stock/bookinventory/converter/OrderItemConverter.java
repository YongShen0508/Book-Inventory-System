package com.stock.bookinventory.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.stock.bookinventory.dto.request.OrderItemRequestDTO;
import com.stock.bookinventory.dto.response.OrderItemResponseDTO;
import com.stock.bookinventory.model.OrderItem;

public class OrderItemConverter {

	public static OrderItemResponseDTO toDTO(OrderItem orderItem) {
		if (orderItem == null) {
			return new OrderItemResponseDTO();
		}

		OrderItemResponseDTO dto = new OrderItemResponseDTO();
		dto.setId(orderItem.getId());
		dto.setOrderId(orderItem.getOrderId());
		dto.setBookId(orderItem.getBookId());
		dto.setQuantity(orderItem.getQuantity());
		dto.setSubtotal(orderItem.getSubtotal());
		dto.setStatus(orderItem.getStatus());
		dto.setCreatedAt(orderItem.getCreatedAt());
		dto.setUpdatedAt(orderItem.getUpdatedAt());

		return dto;
	}

	public static OrderItem toModel(OrderItemResponseDTO dto) {
		if (dto == null) {
			return new OrderItem();
		}

		OrderItem orderItem = new OrderItem();
		orderItem.setId(dto.getId());
		orderItem.setOrderId(dto.getOrderId());
		orderItem.setBookId(dto.getBookId());
		orderItem.setQuantity(dto.getQuantity());
		orderItem.setSubtotal(dto.getSubtotal());
		orderItem.setStatus(dto.getStatus());
		orderItem.setCreatedAt(dto.getCreatedAt());
		orderItem.setUpdatedAt(dto.getUpdatedAt());
		return orderItem;
	}

	public static OrderItem toModel(OrderItemRequestDTO dto) {
		if (dto == null) {
			return new OrderItem();
		}

		OrderItem orderItem = new OrderItem();
		orderItem.setBookId(dto.getBookId());
		orderItem.setQuantity(dto.getQuantity());
		return orderItem;
	}

	public static List<OrderItemResponseDTO> toDTOList(List<OrderItem> orderItems) {
		if (orderItems == null || orderItems.isEmpty()) {
			return new ArrayList<>();
		}

		return orderItems.stream().map(OrderItemConverter::toDTO).collect(Collectors.toList());
	}

}
