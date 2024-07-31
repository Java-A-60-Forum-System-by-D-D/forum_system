package com.example.ForumProject.models.dto;


import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.UserRole;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class UserSummaryDTO {


    private int id;
    private String username;

    private String email;

    private String photoURL;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private Set<UserRole> userRole;

    private Set<Post> posts;

    private boolean isBlocked;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public UserSummaryDTO() {
    }
}
