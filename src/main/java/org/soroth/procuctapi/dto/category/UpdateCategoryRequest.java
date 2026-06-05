package org.soroth.procuctapi.dto.category;

public record UpdateCategoryRequest(
        String name,
        String description,
        Float price
) {
}
