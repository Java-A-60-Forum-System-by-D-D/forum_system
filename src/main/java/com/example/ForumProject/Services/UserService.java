package com.example.ForumProject.Services;

import com.example.ForumProject.models.User;

import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByFirstName(String firstName);

    User createUser(User user);

    User updateUser(User user);

    boolean verifyPassword(User user, String rawPassword);

    void logout();
    boolean login(User user);
}
