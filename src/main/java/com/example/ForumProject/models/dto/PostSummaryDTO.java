package com.example.ForumProject.models.dto;

import com.example.ForumProject.models.persistentClasses.Category;
import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Tag;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Set;


@Data
public class PostSummaryDTO {

    @JsonIgnore
    private int id;
    private String title;
    private String content;
    private String username;
    private int likesCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Set<Comment> comments;
    private Set<Tag> tags;
    private String category;


    public PostSummaryDTO() {
    }


}
