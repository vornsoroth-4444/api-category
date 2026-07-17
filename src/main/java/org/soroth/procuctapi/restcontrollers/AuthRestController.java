package org.soroth.procuctapi.restcontrollers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.auth.RegisterRequest;
import org.soroth.procuctapi.dto.auth.RegisterResponse;
import org.soroth.procuctapi.service.AuthService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthRestController {
    private final AuthService authService;
   public RegisterResponse register(@Valid @RequestBody RegisterRequest request){
       return authService.register(request);
   }
}
