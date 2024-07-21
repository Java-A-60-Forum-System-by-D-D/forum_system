package com.example.ForumProject.Services;

import com.example.ForumProject.models.Comment;
import com.example.ForumProject.models.Post;
import com.example.ForumProject.models.User;

import java.util.List;

public interface CommentService {
    List<Comment> getComments();
    Comment getCommentById(int id, Post post);
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment, User user);
    void deleteComment(int id, User user);
}
