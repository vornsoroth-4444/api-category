package org.soroth.procuctapi.dto.request;

import java.math.BigDecimal;

public record UpdateProductRequest(
        String name,
        String description,
        BigDecimal price
) {
}
