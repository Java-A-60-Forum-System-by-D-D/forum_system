package com.example.ForumProject.models.dto;


import lombok.Getter;

@Getter
public class FilterPostsDTO {

    private String title;
    private String content;
    private Integer userId;
    private Integer tagId;
    private String sortBy;
    private String sortOrder;

    public FilterPostsDTO() {
    }
}
