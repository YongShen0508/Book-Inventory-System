package com.stock.bookinventory.dto.request;

/**
 * Validation group marker interface for create operations. Used to specify
 * which validation constraints should be applied during entity creation.
 *
 * Example usage:
 * 
 * @NotNull(groups = CreateValidation.class, message = "Field is required for
 *                 creation")
 */
public interface CreateValidation {
}
