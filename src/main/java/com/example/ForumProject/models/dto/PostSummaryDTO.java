package com.example.ForumProject.models.dto;

import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Like;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.example.ForumProject.models.persistentClasses.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.PrimitiveIterator;
import java.util.Set;


@Data
public class PostSummaryDTO {


    private String title;
    private String content;
    private String username;
    private int likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Comment> comments;
    private Set<Tag> tags;





    public PostSummaryDTO() {
    }



}
