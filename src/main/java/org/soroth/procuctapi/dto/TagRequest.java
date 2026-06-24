package org.soroth.procuctapi.dto;

import lombok.Builder;

@Builder
public record TagRequest(
        String name
) {
}
