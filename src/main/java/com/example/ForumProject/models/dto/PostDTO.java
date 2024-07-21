package com.example.ForumProject.models.dto;

import com.example.ForumProject.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostDTO {


    @Size(min = 16, max = 64, message = "Title must be between 16 and 64 symbols")
    private String title;

    @Size(min = 32, max = 8192, message = "Content must be between 32 and 8192 symbols")
    private String content;

    public PostDTO() {
    }





}

