package org.soroth.procuctapi.dto.request;

public record UpdateCategoryRequest(
        String name,
        String description,
        Float price
) {
}
