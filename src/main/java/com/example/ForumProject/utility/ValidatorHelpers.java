package com.example.ForumProject.utility;

import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.models.dto.PostSummaryDTO;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.persistentClasses.UserRole;

import java.util.Objects;
import java.util.Set;

public class ValidatorHelpers {

    public static void roleAuthenticationValidator(User user, UserRole userRole, Post existingPost, String errorMessage) {

        Set<UserRole> userRoleSet = user.getUserRole();

        if (!userRoleSet
                .contains(userRole) && !Objects.equals(existingPost.getUser()
                                                                   .getUsername(), user.getUsername())) {
            throw new AuthorizationException(errorMessage);
        }
    }

    public static void roleAuthenticationValidator(User user, UserRole userRole, PostSummaryDTO existingPost, String errorMessage) {

        Set<UserRole> userRoleSet = user.getUserRole();

        if (!userRoleSet
                .contains(userRole) && !Objects.equals(existingPost.getUsername()
                , user.getUsername())) {
            throw new AuthorizationException(errorMessage);
        }
    }


    public static void roleAuthenticationValidator(User user, UserRole userRole, Comment existingComment, String errorMessage) {

        Set<UserRole> userRoleSet = user.getUserRole();

        if (!userRoleSet
                .contains(userRole) && !Objects.equals(existingComment.getUser()
                                                                      .getUsername(), user.getUsername())) {
            throw new AuthorizationException(errorMessage);
        }
    }


    public static void roleAuthenticationValidator(User user, UserRole userRole, String errorMessage) {
        if (!user.getUserRole()
                 .contains(userRole)) {
            throw new AuthorizationException(errorMessage);
        }
    }


}
