package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller

public class AuthenticationMVCController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;

    public AuthenticationMVCController(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("user", new LoggInUserDTO());
        model.addAttribute("registerUser", new UserDTO());
        return "SignUp";
    }
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute ("user") LoggInUserDTO loggInUserDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()){
            return "SignUp";
        }
        authenticationService.loginUser(loggInUserDTO.getUsername(), loggInUserDTO.getPassword());
        return "redirect:/";
    }
//    @GetMapping("/register")
//    public String register(Model model) {
//        model.addAttribute("registerUser", new UserDTO());
//        return "SignUp";
//    }

    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUser") UserDTO userDTO, BindingResult bindingResult, Model model) {
        User user = userMapper.createUserFromDto(userDTO);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
        }
        authenticationService.createUser(user);
        model.addAttribute("user", user);
        return "redirect:/login";
    }
}
