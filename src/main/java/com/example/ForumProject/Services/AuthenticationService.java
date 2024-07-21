package com.example.ForumProject.Services;

import com.example.ForumProject.models.User;

public interface AuthenticationService {
    User createUser(User user);

    String loginUser(String username, String password);
}
