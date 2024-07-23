package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsersPosts;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;

public interface UserRepository {
    List<User> getUsers();

    User getUserById(int id);

    List<User> getUserByUsername(String username);

    User getUserByFirstName(String firstName);

    List<User> getUserByEmail(String email);

    User updateUser(User user);

    User createUser(User user);
    List<Post> getUsersPosts(User user, FilterOptionsUsersPosts filterOptionsUsersPosts);

}
