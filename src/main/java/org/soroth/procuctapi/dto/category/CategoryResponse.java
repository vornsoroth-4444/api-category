package org.soroth.procuctapi.dto.category;

public record CategoryResponse(
        Integer id,
        String name,
        String description,
        Float price
) {
}
