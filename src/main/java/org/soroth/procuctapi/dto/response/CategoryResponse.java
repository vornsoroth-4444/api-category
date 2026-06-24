package org.soroth.procuctapi.dto.response;

import lombok.Builder;

import java.util.List;


@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        boolean isDelete,
        List<CategoryResponse> subCategories
//        Float price
) {
}
