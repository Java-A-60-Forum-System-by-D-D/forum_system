package com.example.ForumProject.Services;

import com.example.ForumProject.models.User;

import java.util.List;

public interface UserService {
    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);

    User createUser(User user);

    User updateUser(User user);

    boolean verifyPassword(User user, String rawPassword);
}
