package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.LoggInUserDTO;

public interface AuthenticationService {
    User createUser(User user);

    LoggInUserDTO loginUser(String username, String password);
}
