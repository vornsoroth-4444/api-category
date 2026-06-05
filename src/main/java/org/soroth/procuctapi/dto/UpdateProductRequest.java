package org.soroth.procuctapi.dto;

public record UpdateProductRequest(
        String name,
        String description,
        Float price
) {
}
