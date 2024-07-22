package com.example.ForumProject.services;

import com.example.ForumProject.helpers.UserMapper;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;
import com.example.ForumProject.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, UserService userService, UserRepository userRepository1, PasswordEncoder passwordEncoder, UserMapper userMapper, TokenService tokenService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository1;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }


    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public LoggInUserDTO loginUser(String username, String password) {


        Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = tokenService.generateJwt(auth);

        LoggInUserDTO loggedUser = new LoggInUserDTO(username, passwordEncoder.encode(password), token);

        return loggedUser;

    }


}
