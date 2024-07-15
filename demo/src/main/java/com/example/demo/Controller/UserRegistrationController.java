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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserRegistrationController {
    private final MyUserService userService;

    private final String emailIsTaken = "Error: Email is taken";

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
    public String postUser(@ModelAttribute MyUserRequest myUserRequest, RedirectAttributes redirectAttributes) {
        System.out.println(myUserRequest.toString());

        /* User Registration filteration */
        if (userService.MyUserExist(myUserRequest)) {
            redirectAttributes.addFlashAttribute("error", emailIsTaken);
            return "redirect:/register?error";
        } else {
            myUserRequest.setPassword(passwordEncoder.encode(myUserRequest.getPassword()));
            userService.registerUser(myUserRequest);

            return "redirect:/register?success";
        }
        
    }
    
}
