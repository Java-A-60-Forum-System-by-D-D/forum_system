package com.example.ForumProject.controllers;

import com.example.ForumProject.services.contracts.AuthenticationService;
import com.example.ForumProject.models.helpers.UserMapper;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.models.dto.UserDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private AuthenticationService authenticationService;
    private UserMapper userMapper;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService, UserMapper userMapper) {
        this.authenticationService = authenticationService;
        this.userMapper = userMapper;
    }

    @PostMapping("/register")
    public User createUser(@Valid @RequestBody UserDTO userDTO) {
        User user = userMapper.createUserFromDto(userDTO);
        return authenticationService.createUser(user);

    }

    @PostMapping("/login")
    public LoggInUserDTO loginUser(@Valid @RequestBody LoggInUserDTO loggInUserDTO) {
        return authenticationService.loginUser(loggInUserDTO.getUsername(), loggInUserDTO.getPassword());
    }




    /*TODO Implement Logout method*/
}
