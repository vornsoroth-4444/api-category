package org.soroth.procuctapi.restcontrollers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.soroth.procuctapi.dto.user.UserUpdateRequest;
import org.soroth.procuctapi.service.AuthService;
import org.soroth.procuctapi.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/test")
@Slf4j
public class TestController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping("profile")
    public UserResponse getProfile(@AuthenticationPrincipal Jwt jwt){
         String keycloakId = (String) jwt.getSubject();
        log.info("profile keycloakId: {}", keycloakId);
        return  userService.getUserByKeycloakId(keycloakId);
    }

//    NOTE: should use dto
    @PostMapping("/forgot-password/{email}")
    public String forgotPassword(@PathVariable String email){
        authService.forgotPassword(email);
        return "Reset Password Link has successfully send to associate account";
    }

    @PostMapping("/profile")
    public UserResponse updateProfile(@AuthenticationPrincipal Jwt jwt ,
                                      @RequestBody UserUpdateRequest request){
        String keycloakId = (String) jwt.getSubject();
        return authService.updateUser(keycloakId,request);
    }

    // only customer user can access this !
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    @GetMapping("/customer")
    public String customer(){
        return  "Hello ! I am the customer !";
    }

    // only admin can
    @PreAuthorize("hasRole('ROLE_SELLER')")
    @PostMapping("/seller")
    public String seller(){
        return  "Hello ! I am the seller !";
    }


}
