package com.example.ForumProject.Services;

import com.example.ForumProject.helpers.UserMapper;
import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.UserDTO;
import com.example.ForumProject.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class AuthenticationServiceImpl {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, UserService userService, UserRepository userRepository1, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository1;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }


}
