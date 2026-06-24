package org.soroth.procuctapi.dto.request;

import lombok.Builder;

@Builder
public record TagRequest(
        String name
) {
}
