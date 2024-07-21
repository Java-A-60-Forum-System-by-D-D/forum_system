package com.example.ForumProject.Services;

import com.example.ForumProject.exceptions.AuthorizationException;
import com.example.ForumProject.exceptions.EntityNotFoundException;
import com.example.ForumProject.models.*;
import com.example.ForumProject.repositories.CommentRepository;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> getComments() {
        return commentRepository.getComments();
    }

    @Override
    public Comment getCommentById(int id, Post post) {
        Comment comment = commentRepository.getCommentById(id);
        if (!post.getComments()
                 .contains(comment)) {
            throw new EntityNotFoundException("Comment", id);
        }


        return comment;
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.createComment(comment);
    }

    @Override
    public Comment updateComment(Comment comment, User user) {
        authenticationValidator(user, comment);
        return commentRepository.updateComment(comment);
    }

    @Override
    public void deleteComment(int id, User user) {
        authenticationValidator(user, commentRepository.getCommentById(id));
        commentRepository.deleteComment(id);

    }

    private static void authenticationValidator(User user, Comment existingComment) {

        Set<UserRole> userRoleSet = user.getUserRole();
        UserRole userRoleAdmin = new UserRole(UserRoleEnum.ADMIN);

        if (!userRoleSet
                .contains(userRoleAdmin) && !Objects.equals(existingComment.getUser()
                                                                           .getUsername(), user.getUsername())) {
            throw new AuthorizationException("You have no permission to perform this action");
        }
    }
}
