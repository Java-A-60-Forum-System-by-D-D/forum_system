package com.example.ForumProject.models.dto;

import com.example.ForumProject.models.persistentClasses.Comment;
import com.example.ForumProject.models.persistentClasses.Tag;
import lombok.Data;

import java.time.LocalDateTime;
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
