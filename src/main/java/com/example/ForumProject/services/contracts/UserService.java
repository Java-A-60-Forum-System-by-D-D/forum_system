package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.dto.UserSummaryDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsPosts;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsers;
import com.example.ForumProject.models.filterOptions.FilterOptionsUsersPosts;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;


import java.util.List;

public interface UserService {
    List<User> getUsers();
    User getUserById(int id);

    User getUserByUsername(String username);

    User getUserByFirstName(String firstName);

    User getUserByEmail(String email);

    User updateUser(User user);

    List<Post> getPostsByUser(User user, FilterOptionsUsersPosts filterOptionsUsersPosts);

    List<User> getUsers(FilterOptionsUsers filterOptionsUsers);
}
