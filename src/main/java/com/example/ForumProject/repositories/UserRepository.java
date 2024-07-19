package com.example.ForumProject.repositories;

import com.example.ForumProject.models.User;

import java.util.List;

public interface UserRepository {
    List<User> getAll();

    User getById(int id);

    User getByUsername(String username);

    User getByFirstName(String firstName);

    List<User> getByEmail(String email);

    User updateUser(User user);

    void createUser(User user);
}
