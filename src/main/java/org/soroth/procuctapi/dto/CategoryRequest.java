package org.soroth.procuctapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record CategoryRequest(
//        @NotBlank(message = "name is required")
//        String name,
//        @NotBlank(message = "description is required")
//        String description,
//        @NotNull(message = "price is required")
//        @Positive(message = "price must be positive")
//        Float price,
        @Size(min = 1, max = 100)
        String name,
        @Size(min = 1, max = 255)
        String description,
        String icon,
        Integer parentCategoryId
) {
}
