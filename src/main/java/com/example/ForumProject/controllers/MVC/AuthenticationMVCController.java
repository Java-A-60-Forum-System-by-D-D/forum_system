package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.services.contracts.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller

public class AuthenticationMVCController {

    private final AuthenticationService authenticationService;
    private final UserMapper userMapper;
    private final UserService userService;

    public AuthenticationMVCController(AuthenticationService authenticationService, UserMapper userMapper, UserService userService) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
        this.userService = userService;
    }


    @GetMapping("/login")
    public String login(Model model) {
        if (!model.containsAttribute("registerUser")) {
            model.addAttribute("registerUser", new UserDTO());
        }
        model.addAttribute("LoginUser", new LoggInUserDTO());
        return "SignUp";
    }



    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("LoginUser") LoggInUserDTO loggInUserDTO, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "SignUp";
        }

        authenticationService.loginUser(loggInUserDTO.getUsername(), loggInUserDTO.getPassword());
        Authentication authentication = SecurityContextHolder.getContext()
                                                             .getAuthentication();
        System.out.println(authentication);
        return "redirect:/";
    }



    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUser") UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addFlashAttribute("registerUser", userDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUser", bindingResult);
            return "redirect:/login";
        }
        User user = userMapper.createUserFromDto(userDTO);
        authenticationService.createUser(user);
        model.addAttribute("LoginUser", user);
        return "redirect:/login";
    }
}
