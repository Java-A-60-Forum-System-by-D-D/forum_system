package com.example.ForumProject.repositories;

import com.example.ForumProject.models.Comment;

import java.util.List;

public class CommentRepositoryImpl implements CommentRepository {
    @Override
    public List<Comment> getComments() {
        return List.of();
    }

    @Override
    public Comment getCommentById(int id) {
        return null;
    }

    @Override
    public Comment addComment(Comment comment) {
        return null;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return null;
    }

    @Override
    public void deleteComment(int id) {

    }
}
