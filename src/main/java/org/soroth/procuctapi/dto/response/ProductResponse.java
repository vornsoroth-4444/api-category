package org.soroth.procuctapi.dto.response;

import java.util.Set;

public record ProductResponse(
        Integer id,
        String name,
        String description,
        Float price,
        CategoryResponse category,
        Set<TagResponse> tags
) { }
