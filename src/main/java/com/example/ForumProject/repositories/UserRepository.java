package com.example.ForumProject.repositories;

import com.example.ForumProject.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    User getUserById(int id);

    List<User> getUserByUsername(String username);

    User getUserByFirstName(String firstName);

    List<User> getUserByEmail(String email);

    User updateUser(User user);

    User createUser(User user);
}
