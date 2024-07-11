package com.example.demo.Controller;

import com.example.demo.Dto.MyUserRequest;
import com.example.demo.Service.MyUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class UserRegistrationController {
    private final MyUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserRegistrationController(MyUserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHomePage() {
        return "home/home";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("myUserRequest", new MyUserRequest());
        return "home/register";
    }

    @PostMapping("/register")
    public String postUser(@ModelAttribute MyUserRequest myUserRequest, Model model) {
        myUserRequest.setPassword(passwordEncoder.encode(myUserRequest.getPassword()));

        userService.registerUser(myUserRequest);

        System.out.println(myUserRequest.toString());

        // String email = userDto.getEmail();

        // if () {
        //     model.addAttribute("invalid", "Invalid");
        //     return "redirect:/register?invalid";
        // }
        
        // We don't want to send the User immediately into the main page.
        // We want to show a message to confirm the user created is successful
        // Otherwise, return error message: (If email is already taken, show error message.)
        // model.addAttribute("success", "Success");

        /* Alternatives for link just in case */
        return "redirect:/register?success";
        // return "redirect:/account";
        // return "home/reg_success";
        // return "home/login";
    }
    
}
