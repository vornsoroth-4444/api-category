package org.soroth.procuctapi.dto;

public record UpdateCategoryRequest(
        String name,
        String description,
        Float price
) {
}
