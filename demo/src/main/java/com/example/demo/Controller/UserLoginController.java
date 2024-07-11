package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class UserLoginController {
    
    @GetMapping("/login")
    public String showLoginPage() {
        return "home/login";
    }
}
