package com.example.ForumProject.models.helpers;

import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Post;
import com.example.ForumProject.models.persistentClasses.User;
import com.example.ForumProject.models.dto.CommentDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {
    private ModelMapper modelMapper;

    public CommentMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    public Comment createFromDto(Post post, CommentDTO commentDTO, User user){
        Comment comment = modelMapper.map(commentDTO, Comment.class);
        comment.setCreatedAt(LocalDateTime.now());
        comment.setUpdatedAt(LocalDateTime.now());
        comment.setUser(user);
        comment.setPost(post);
        return comment;
    }
    public Comment updateFromDto(Comment existingComment, CommentDTO commentDTO, User user) {
        modelMapper.map(commentDTO, existingComment);
        existingComment.setUpdatedAt(LocalDateTime.now());
        existingComment.setUser(user);
        return existingComment;
    }
}
