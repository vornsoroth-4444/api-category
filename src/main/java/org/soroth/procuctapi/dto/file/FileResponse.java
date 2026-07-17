package org.soroth.procuctapi.dto.file;

import lombok.Builder;

@Builder
public record FileResponse(
        String name,
        String caption,
        String extension,
        Long size,
        String url
) {
}
