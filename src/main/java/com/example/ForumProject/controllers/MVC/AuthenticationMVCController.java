package com.example.ForumProject.controllers.MVC;

import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.models.dto.UserMVCDTO;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.services.contracts.UserService;
import com.example.ForumProject.services.implementations.AuthenticationServiceMVC;
import jakarta.servlet.ServletException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

@Controller

public class AuthenticationMVCController {

    private final AuthenticationServiceMVC authenticationService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final AuthenticationFailureHandler authenticationFailureHandler;


    @Autowired
    public AuthenticationMVCController(AuthenticationServiceMVC authenticationService,
                                       UserMapper userMapper,
                                       @Qualifier("mvcAuthenticationManager") AuthenticationManager authenticationManager,
                                       AuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }


    @GetMapping("/login")
    public String login(Model model) {
        if(!model.containsAttribute("isFound")) {
            model.addAttribute("isFound", true);
        }
        if (!model.containsAttribute("registerUser")) {
            model.addAttribute("registerUser", new UserMVCDTO());
        }
        model.addAttribute("LoginUser", new LoggInUserDTO());
        return "SignUp";
    }


    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("LoginUser") LoggInUserDTO loggInUserDTO,
                        BindingResult bindingResult,
                        HttpServletRequest request,
                        HttpServletResponse response, Model model) throws ServletException, IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "SignUp";
        }

        try {
            Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loggInUserDTO.getUsername(),
                    loggInUserDTO.getPassword()));

            SecurityContextHolder.getContext()
                                 .setAuthentication(auth);
            return "redirect:/home";

        } catch (AuthenticationException e) {
            authenticationFailureHandler.onAuthenticationFailure(request, response, e);
            model.addAttribute("isFound", false);

            return "SignUp";


        }


    }


    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("registerUser") UserMVCDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {

            model.addAttribute("hasErrors", true);
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("LoginUser", new LoggInUserDTO());
            model.addAttribute("showSignUp", true);
            redirectAttributes.addFlashAttribute("registerUser", userDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.registerUser", bindingResult);
            return "SignUp";
        }
        User user = userMapper.createUserMVCFromDto(userDTO);
        authenticationService.createUser(user);
        model.addAttribute("LoginUser", user);
        return "redirect:/login";
    }


}
