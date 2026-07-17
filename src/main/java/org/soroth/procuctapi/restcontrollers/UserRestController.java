package org.soroth.procuctapi.restcontrollers;

import lombok.RequiredArgsConstructor;
import org.soroth.procuctapi.dto.user.CreateUserRequest;
import org.soroth.procuctapi.dto.user.UserResponse;
import org.soroth.procuctapi.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserRestController {
    private  final  UserService userService;

    @GetMapping
    public  List<UserResponse>getUser(){
        return  userService.getAllUser();
    }
    @PostMapping
    public UserResponse createNew(@RequestBody CreateUserRequest request){
        return userService.createUser(request);
    }
}
