package org.soroth.procuctapi.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.soroth.procuctapi.dto.auth.RegisterRequest;
import org.soroth.procuctapi.dto.auth.RegisterResponse;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.soroth.procuctapi.dto.user.UserUpdateRequest;
import org.soroth.procuctapi.entity.Profile;
import org.soroth.procuctapi.entity.User;
import org.soroth.procuctapi.mapper.UserMapper;
import org.soroth.procuctapi.repository.ProfileRepository;
import org.soroth.procuctapi.repository.UserRepository;
import org.soroth.procuctapi.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private  final UserRepository userRepository;
    private ProfileRepository profileRepository;
    //client used to create manage the user in KC
    private  final Keycloak keycloak;
    private final UserMapper userMapper;
    @Value("${keycloak.realm}")
    private String realm;
    @Value("${keycloak.client-id}")
    private String clientId;

    private ClientRepresentation getClientById(String clientId){
        return keycloak.realm(realm)
                .clients()
                .findByClientId(clientId)
                .stream()
                .findFirst()
                .orElseThrow(()-> new RuntimeException("Client with id = "+clientId+" not found in Keycloak"));
    }
    private UserRepresentation createUserInKeycloak( RegisterRequest registerRequest){
        // 1. user representation -> store basic information (idm)
        var useRepresentation = new UserRepresentation();
        useRepresentation.setUsername(registerRequest.username());
        useRepresentation.setEmail(registerRequest.email());
        useRepresentation.setFirstName(registerRequest.firstName());
        useRepresentation.setLastName(registerRequest.lastName());
        //emailVerified , enableAccount
        useRepresentation.setEnabled(true);
        useRepresentation.setEmailVerified(false);
        useRepresentation.setRequiredActions(List.of("VERIFY_EMAIL"));

        // customize more info of the user in keycloak (Optional)
        // you will need to create this inside your keycloak as well
        Map<String , List<String>> attributes = new HashMap<>();
        attributes.put("gender", List.of(registerRequest.gender()));
        attributes.put("biography", List.of(registerRequest.biography()));

        useRepresentation.setAttributes(attributes);
        // credentials  -> password
        var cred = new CredentialRepresentation();
        cred.setTemporary(false);
        cred.setType(CredentialRepresentation.PASSWORD);
        cred.setValue(registerRequest.password());
        // setting the password for this new user
        useRepresentation.setCredentials(List.of(cred));

        var resourceResource = keycloak.realm(realm).users();
        try( var response = resourceResource.create(useRepresentation)){
            // confirm if the user is created  , we will configure more
            if (response.getStatus() == 201){
                // we will assign them the default role
                // all register use will be in CUSTOMER ROLE
                String userId = CreatedResponseUtil.getCreatedId(response);
                UserResource userResource = keycloak.realm(realm).users().get(userId);

                // assign the ROLE for the user in keycloak
                var client = getClientById(clientId);
                // create role representation ( role inside keycloak)
                var roleRepresentation = keycloak.realm(realm)
                        .clients().get(client.getId())
                        .roles( ).get("CUSTOMER").toRepresentation();

                //add role to keycloak user
                userResource.roles()
                        .clientLevel(client.getId())
                        .add(List.of(roleRepresentation));
                log.info("Sending email verification to user: {}", useRepresentation.getEmail());
                userResource.sendVerifyEmail();

              useRepresentation.setId(userId);
              return useRepresentation;
            }else {
                throw new NoSuchElementException("Failed to create user in Keycloak: " + response.getStatusInfo().getReasonPhrase());
            }

        } catch (Exception ex){
            ex.printStackTrace();
            log.error("Error creating user in Keycloak: {}", ex.getMessage());
            throw new RuntimeException("Error creating user in Keycloak: " + ex.getMessage(), ex);
        }
//
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // ensure that password matches
        if(!request.password().equals(request.confirmedPassword())) {
            throw new RuntimeException("Passwords don't match");
        }
        var kcResponse= createUserInKeycloak(request);
        User user = new User();
        // kcResponse.id() -> normal id not keycloak id
        log.info("Value of KC ID : {}", kcResponse.getId());

        user.setKeycloakId(kcResponse.getId());
        user.setEmail(kcResponse.getEmail());
        user.setUsername(kcResponse.getUsername());

        Profile profile = new Profile();
        profile.setFirstName(kcResponse.getFirstName());
        profile.setLastName(kcResponse.getLastName());
        profile.setGender(request.gender());
        profile.setBio(request.biography());
        profile.setUser(user);

        // profile.setProfileUrl(request.profileUrl);
        user.setProfile(profile);
        var createdUser = userRepository.save(user);
        return userMapper.toRegisterResponse(createdUser);

    }

    public UserResponse updateUser (String keycloakId, UserUpdateRequest request){
        // 1. update the spring side first
        var oldUser = userRepository.findByKeycloakId(keycloakId)
                .orElseThrow(()-> new  NoSuchElementException ("User with id" + keycloakId + "not found"));
        var oldProfile = oldUser.getProfile();
        if (request.firstName() != null)
            oldProfile.setFirstName(request.firstName());
        if (request.lastName() != null)
            oldProfile.setLastName(request.lastName());
        if (request.gender() != null)
            oldProfile.setGender(request.gender());
        if (request.biography() != null)
            oldProfile.setBio(request.biography());

        oldUser.setProfile(oldProfile);
        var updatedUser = userRepository.save(oldUser);

        // 2. update the keycloak side
        try{
            var userResource = keycloak
                    .realm(realm)
                    .users().get(keycloakId);
            var userRep = userResource.toRepresentation();
            if (request.firstName() != null)
                userRep.setFirstName(request.firstName());
            if (request.lastName() != null)
                userRep.setLastName(request.lastName());

            Map<String, List<String>> attributes = (userRep
                    .getAttributes() != null ?
                    new HashMap<>(userRep.getAttributes())
                    :new HashMap<>()
                    );
            if (request.gender() != null)
                attributes.put("gender", List.of(request.gender()));
            if (request.biography() != null)
                attributes.put("biography" , List.of(request.biography()));
            // update attribute
            userRep.setAttributes(attributes);
            userResource.update(userRep); // update the whole kc user
            return  userMapper.toUserResponse(updatedUser);
        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Error user in keycloak ", ex);
            throw  new RuntimeException("Error user in keycloak");
        }

    }

    // forgot password
    public void forgotPassword(String email){
        try{
            var listUserRepresentation = keycloak
                    .realm(realm)
                    .users()
                    .searchByEmail(email , true);
            // exact = true , exact match
            if(listUserRepresentation.isEmpty()){
                log.warn("Sending reset password to no-existent user with email {}",email);
                return;
            }
            var kcUserId= listUserRepresentation.getFirst().getId();
            var userResource = keycloak.realm(realm).users().get(kcUserId);

            log.info("sending reset password to user with id {}", kcUserId);
            userResource.executeActionsEmail(List.of("UPDATE_PASSWORD"));

        }catch (Exception ex){
            ex.printStackTrace();
            log.error("Error saving user in keycloak", ex);
            throw  new RuntimeException("Error saving user in keycloak");
        }
    }
}
