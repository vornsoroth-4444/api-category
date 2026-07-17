package org.soroth.procuctapi.service;

import org.soroth.procuctapi.dto.user.CreateUserRequest;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UserService {
    UserResponse createUser(CreateUserRequest createUserRequest);
    List<UserResponse> getAllUser();
    UserResponse getUserByKeycloakId(String keycloakId);
}
