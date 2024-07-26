package com.example.ForumProject.repositories.contracts;

import com.example.ForumProject.models.dto.CommentDTO;
import com.example.ForumProject.models.filterOptions.FilterOptionsComments;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.User;

import java.util.List;

public interface CommentRepository {
    List<Comment> getComments();
    List<Comment> getCommentsByUser(User user, FilterOptionsComments filterOptionsComments);
    Comment getCommentById(int id);
    Comment createComment(Comment comment);
    Comment updateComment(Comment comment);
    void deleteComment(int id);

}
