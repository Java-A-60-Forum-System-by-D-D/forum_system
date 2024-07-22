package com.example.ForumProject.services;

import com.example.ForumProject.models.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;

public interface AuthenticationService {
    User createUser(User user);

    LoggInUserDTO loginUser(String username, String password);
}
