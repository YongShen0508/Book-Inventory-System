package com.stock.bookinventory.model;

import java.util.Set;

import lombok.Getter;

@Getter
public enum OrderStatus {

	PENDING("PENDING", Set.of("CONFIRMED", "CANCELLED", "EXPIRED")), CONFIRMED("CONFIRMED",
			Set.of("SHIPPED", "CANCELLED")), SHIPPED("SHIPPED", Set.of("DELIVERED", "CANCELLED")), DELIVERED(
					"DELIVERED", Set.of("COMPLETED")), COMPLETED("COMPLETED",
							Set.of()), CANCELLED("CANCELLED", Set.of()), EXPIRED("EXPIRED", Set.of());

	private final String label;
	private final Set<String> allowedTransitions;

	OrderStatus(String label, Set<String> allowedTransitions) {
		this.label = label;
		this.allowedTransitions = allowedTransitions;
	}

	public boolean canTransitionTo(OrderStatus newStatus) {
		return allowedTransitions.contains(newStatus.label);
	}

	public boolean isTerminal() {
		return allowedTransitions.isEmpty();
	}

	public boolean isSuccessful() {
		return this == COMPLETED;
	}

	public boolean isFailed() {
		return this == CANCELLED || this == EXPIRED;
	}

	public Set<String> getAllowedNextStatus() {
		return this.allowedTransitions;
	}

	public void validateTransition(OrderStatus newStatus) {
		if (this == newStatus) {
			throw new IllegalStateException(String.format("Order is already in %s status", this.label));
		}

		if (this.isTerminal()) {
			throw new IllegalStateException(String.format("Cannot transition from terminal status %s", this.label));
		}

		if (!this.canTransitionTo(newStatus)) {
			throw new IllegalStateException(
					String.format("Cannot transition from %s to %s", this.label, newStatus.label));
		}
	}
}
