package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.auth.RegisterRequest;
import org.soroth.procuctapi.dto.auth.RegisterResponse;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.soroth.procuctapi.dto.user.UserUpdateRequest;

public interface AuthService {
    // register the new user
    RegisterResponse register(RegisterRequest request);
    UserResponse updateUser(String keycloakId, UserUpdateRequest request);
    void forgotPassword(String email);

}
