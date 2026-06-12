package org.soroth.procuctapi.dto;

import lombok.Builder;

@Builder
public record CategoryResponse(
        Integer id,
        String name,
        String description,
        boolean isDelete
//        Float price
) {
}
