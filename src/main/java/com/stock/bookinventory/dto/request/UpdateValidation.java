package com.stock.bookinventory.dto.request;

/**
 * Validation group marker interface for update operations. Used to specify
 * which validation constraints should be applied during entity updates.
 *
 * Example usage:
 * 
 * @NotNull(groups = UpdateValidation.class, message = "Field is required for
 *                 update")
 */
public interface UpdateValidation {
}
