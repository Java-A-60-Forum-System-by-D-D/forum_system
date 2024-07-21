package com.example.ForumProject.repositories;

import com.example.ForumProject.models.Comment;

import java.util.List;

public interface CommentRepository {
    List<Comment> getComments();
    Comment getCommentById(int id);
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    void deleteComment(int id);

}
