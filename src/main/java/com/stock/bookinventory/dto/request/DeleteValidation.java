package com.stock.bookinventory.dto.request;

/**
 * Validation group marker interface for delete operations. Used to specify
 * which validation constraints should be applied during entity deletion.
 *
 * Example usage:
 * 
 * @NotNull(groups = DeleteValidation.class, message = "ID is required for
 *                 deletion")
 */
public interface DeleteValidation {
}
