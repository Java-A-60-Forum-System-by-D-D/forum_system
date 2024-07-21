package com.example.ForumProject.Controllers;

import com.example.ForumProject.Services.AuthenticationServiceImpl;
import com.example.ForumProject.helpers.UserMapper;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationServiceImpl authenticationService;
    private UserMapper userMapper;

    @Autowired
    public AuthenticationController(AuthenticationServiceImpl authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.createFromDto(userDTO);
        return authenticationService.createUser(user);

    }

    @PostMapping("/login")
    public String loginUser(@Valid @RequestBody LoggInUserDTO loggInUserDTO) {
        return authenticationService.loginUser(loggInUserDTO.getUsername(), loggInUserDTO.getPassword());

    }
}
