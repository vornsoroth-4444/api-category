package org.soroth.procuctapi.dto.user;

public record UserResponse(
        Long id,
        String email,
        String profileUrl,
        String bio
) {
}
