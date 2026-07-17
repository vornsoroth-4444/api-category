package org.soroth.procuctapi.mapper;

import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.soroth.procuctapi.dto.auth.RegisterResponse;
import org.soroth.procuctapi.dto.user.CreateUserRequest;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.soroth.procuctapi.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "profileUrl",source= "profile.profileUrl")
    @Mapping(target = "bio",source = "profile.bio")
    UserResponse toUserResponse(User user);
    User toUser(CreateUserRequest request);

    //for register
    RegisterResponse toRegisterResponse(UserRepresentation user);

    // entity to registerResponse
    @Mapping(target = "firstName", source = "profile.firstName")
    @Mapping(target ="lastName", source = "profile.lastName")
    @Mapping(target = "biography" , source="profile.bio")
    @Mapping(target = "gender", source = "profile.gender")
    RegisterResponse toRegisterResponse(User user);
}
