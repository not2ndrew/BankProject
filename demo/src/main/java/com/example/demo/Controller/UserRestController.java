package com.example.demo.Controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Dto.MyUserRequest;
import com.example.demo.Service.MyUserService;

@RestController
public class UserRestController {
    private final MyUserService userService;

    public UserRestController(MyUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<MyUserRequest> getAllUsers() {
        return userService.getAllUsers();
    }
}
