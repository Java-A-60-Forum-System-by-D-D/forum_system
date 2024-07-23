package com.example.ForumProject.services.contracts;

import com.example.ForumProject.models.filterOptions.FilterOptionsComments;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;

public interface CommentService {
    List<Comment> getComments();
    List<Comment> getCommentsByUser(User user, FilterOptionsComments filterOptionsComments);
    Comment getCommentById(int id, Post post);
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment, User user);
    void deleteComment(int id, User user);
}
