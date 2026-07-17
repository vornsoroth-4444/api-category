package org.soroth.procuctapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.user.CreateUserRequest;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.soroth.procuctapi.entity.Profile;
import org.soroth.procuctapi.mapper.UserMapper;
import org.soroth.procuctapi.repository.ProfileRepository;
import org.soroth.procuctapi.repository.UserRepository;
import org.soroth.procuctapi.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProfileRepository profileRepository;

    @Override
    public UserResponse createUser(CreateUserRequest request) {
        //
        var user = userMapper.toUser(request);
        var profile = new  Profile();
        profile.setBio(request.bio());
        profile.setProfileUrl(request.profileUrl());
        profile.setUser(user);
        user.setProfile(profile);
        return  userMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public List<UserResponse> getAllUser() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse getUserByKeycloakId(String keycloakId) {
        return userMapper.toUserResponse(userRepository.findByKeycloakId(keycloakId).orElseThrow(
                ()-> new NoSuchElementException("user not found with id: " + keycloakId)
        ));
    }
}
