package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;


import java.util.List;

public interface UserService {
    List<User> getUsers();

    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByFirstName(String firstName);

    User createUser(User user);

    User updateUser(User user);

    boolean verifyPassword(User user, String rawPassword);


    List<Tag> getUserTags(User user);
}
