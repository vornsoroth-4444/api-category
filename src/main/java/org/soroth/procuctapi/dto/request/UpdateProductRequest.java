package org.soroth.procuctapi.dto.request;

public record UpdateProductRequest(
        String name,
        String description,
        Float price
) {
}
